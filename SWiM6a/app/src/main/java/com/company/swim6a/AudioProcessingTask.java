package com.company.swim6a;

import java.util.concurrent.LinkedBlockingQueue;

class AudioProcessingTask implements Runnable {
    private LinkedBlockingQueue<byte[]> buffers;
    private LinkedBlockingQueue<byte[]> processedBuffers;
    public static boolean endRecording;
    private final static short THRESHOLD = 15000;

    AudioProcessingTask(LinkedBlockingQueue<byte[]> buffers, LinkedBlockingQueue<byte[]> processedBuffers) {
        this.buffers = buffers;
        this.processedBuffers = processedBuffers;
    }

    @Override
    public void run() {
        while (!endRecording) {
            while (!buffers.isEmpty()) {
                try {
                    byte[] buffer = buffers.poll();

                    boolean addBuffer = false;
                    double sum = 0;
                    for (int i = 0; i < buffer.length; i += 2) {
                        short pcm = (short) (buffer[i] << 8 | buffer[i + 1]);
                        sum += Math.abs(pcm);
//                        if(pcm >= RecordActivity.thresholdSeekBar.getProgress()) {
//                            addBuffer = true;
//                            break;
//                        }
//                        if (Math.abs(pcm) >= RecordActivity.thresholdSeekBar.getProgress()) {
//                            addBuffer = true;
//                            break;
//                        }
                    }
                    double avg = sum / 8192;
                    if (avg >= RecordActivity.thresholdSeekBar.getProgress())
                        addBuffer = true;

                    if (addBuffer)
                        processedBuffers.put(buffer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
