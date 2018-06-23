package com.company.swim6a;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

public class AudioRecorder {

    private LinkedBlockingQueue<byte[]> buffers;
    private LinkedBlockingQueue<byte[]> processedBuffers;

    private AudioGrabbingTask provider;
    private AudioProcessingTask processor;

    private boolean initialized = false;

    public AudioRecorder() {
        buffers = new LinkedBlockingQueue<>();
        processedBuffers = new LinkedBlockingQueue<>();
    }

    public static void connectNotes(File a, File b) {
        try {
            RandomAccessFile raf1 = new RandomAccessFile(a, "r");
            RandomAccessFile raf2 = new RandomAccessFile(b, "r");

            byte[] bytesFile1 = new byte[(int) (raf1.length())];
            raf1.readFully(bytesFile1);
            byte[] bytesFile2 = new byte[(int) raf2.length()];
            raf2.readFully(bytesFile2);

            raf1.close();
            raf2.close();

            byte[] output = new byte[bytesFile1.length + bytesFile2.length - 44];
            System.arraycopy(bytesFile1, 0, output, 0, bytesFile1.length);
            System.arraycopy(bytesFile2, 44, output, bytesFile1.length, bytesFile2.length - 44);

            byte[] header = getWAVHeader(44100, 16, output.length - 44, 1);
            System.arraycopy(header, 0, output, 0, 44);

            FileOutputStream str = new FileOutputStream(a);
            str.flush();
            str.write(output);
            str.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        provider = new AudioGrabbingTask(buffers);
        new Thread(provider).start();
        processor = new AudioProcessingTask(buffers, processedBuffers);
        new Thread(processor).start();

        initialized = true;
    }

    public void pause() {
        provider.pause();
    }

    public void start() {
        if (!initialized)
            initialize();
        else
            provider.resume();
    }

    public void saveToFile(String filename) {
        byte[] result = convertToBytes();

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/notatek");
        myDir.mkdirs();

        String fname = filename + ".wav";
        File file = new File(myDir, fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(result);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        buffers.clear();
        processedBuffers.clear();
    }

    /**
     * Generates a WAV header using the information provided.
     *
     * @param sampleRate      - the sample rate
     * @param bitsPerSample   - the number of bits used per sample
     * @param audioDataLength - the number of audio samples
     * @param numChannels     - the number of audio channels
     * @return the WAV header as an array of bytes
     */
    private static byte[] getWAVHeader(int sampleRate, int bitsPerSample, int audioDataLength, int numChannels) {
        byte[] header = new byte[44];
        //NOTE all fields are little-endian unless they contain characters
        //See https://ccrma.stanford.edu/courses/422/projects/WaveFormat/

        /* RIFF HEADER */
        //ChunkID:
        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        //ChunkSide = size of entire file - 8 bytes for ChunkID and ChunkSize
        int totalLength = audioDataLength + 36; //44 bytes for header - 8 bytes
        header[4] = (byte) (totalLength & 0xff); //mask all but LSB
        header[5] = (byte) ((totalLength >> 8) & 0xff); //shift right and mask all but LSB (now 2nd LSB)
        header[6] = (byte) ((totalLength >> 16) & 0xff); //etc
        header[7] = (byte) ((totalLength >> 24) & 0xff);
        //Format:
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';

        /* "fmt " (note space) SUBCHUNK */
        //SubChunk1ID:
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        //SubChunk1Size:
        header[16] = 16;  //16 for PCM (remember little-endian)
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        //AudioFormat:
        header[20] = 1;  //1 for PCM (linear quantization; no compression)
        header[21] = 0;
        //NumChannels:
        header[22] = (byte) numChannels;
        header[23] = 0;
        //SampleRate:
        header[24] = (byte) (sampleRate & 0xff);
        header[25] = (byte) ((sampleRate >> 8) & 0xff);
        header[26] = (byte) ((sampleRate >> 16) & 0xff);
        header[27] = (byte) ((sampleRate >> 24) & 0xff);
        //ByteRate = SampleRate*NumChannels*BitsPerSample/8:
        int byteRate = numChannels * sampleRate * bitsPerSample / 8;
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        //BlockAlign = NumChannels*BitsPerSample/8;
        header[32] = (byte) (2 * 16 / 8);
        header[33] = 0;
        //BitsPerSample:
        header[34] = (byte) bitsPerSample;
        header[35] = 0;

        /* "data" SUBCHUNK */
        //SubChunk2ID:
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        //SubChunk2Size:
        header[40] = (byte) (audioDataLength & 0xff);
        header[41] = (byte) ((audioDataLength >> 8) & 0xff);
        header[42] = (byte) ((audioDataLength >> 16) & 0xff);
        header[43] = (byte) ((audioDataLength >> 24) & 0xff);
        //actual data will go here
        return header;
    }

    private byte[] convertToBytes() {
        int totalAudioLen = processedBuffers.size() * 8192;
        byte[] header = getWAVHeader(44100, 16, totalAudioLen, 1);
        byte[] result = Arrays.copyOf(header, totalAudioLen + 44);

        int i = 44;
        byte[] buffer;
        while ((buffer = processedBuffers.poll()) != null) {
            for (int j = 0; j < 8192; j++) {
                result[i++] = buffer[j];
            }
        }

        return result;
    }
}
