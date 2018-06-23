package com.company.swim6a;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.util.concurrent.LinkedBlockingQueue;

class AudioGrabbingTask implements Runnable {

    private AudioRecord recorder;
    private LinkedBlockingQueue<byte[]> buffers;
    private int bufferSize;
    //    private Boolean onAir;
    public static boolean endRecording;

    static {
        endRecording = false;
    }

    AudioGrabbingTask(LinkedBlockingQueue<byte[]> buffers) {
        this.buffers = buffers;
        bufferSize = 8192;

        recorder = new AudioRecord(
                MediaRecorder.AudioSource.MIC,
                44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);
        recorder.startRecording();
    }

    @Override
    public void run() {
        while (!endRecording) {
            byte[] buffer = new byte[bufferSize];
            if (recorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                int samplesSize = recorder.read(buffer, 0, buffer.length);
                if (samplesSize >= 0) {
                    double sum = 0;
                    for (int i = 0; i < buffer.length; i += 2) {
                        short buff = (short) (buffer[i] << 8 | buffer[i + 1]);
                        sum += buff;
                    }
                    double avg = sum / samplesSize;

                    RecordActivity.volumeProgressBar.setProgress(2000 - (int) avg);

                    try {
                        buffers.put(buffer);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void pause() {
        recorder.stop();
    }

    public void resume() {
        recorder.startRecording();
    }
}
