package com.cea.celibrary.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PlayerManager {

    private static PlayerManager playerManager;
    /**
     * 外放模式
     */
    public static final int MODE_SPEAKER = 0;
    /**
     * 耳机模式
     */
    public static final int MODE_HEADSET = 1;
    /**
     * 听筒模式
     */
    public static final int MODE_EARPIECE = 2;
    private MediaPlayer mediaPlayer;
    private PlayCallback callback;
    private Context context;
    private AudioManager audioManager;

    private String filePath;
    private MediaRecorder recorder;
    private String voiceFileName;
    private int currentMode = MODE_SPEAKER;

    private OnVoiceRecordingListener onVoiceRecordingListener;

    public static PlayerManager getManager(Context context) {
        if (playerManager == null) {
            synchronized (PlayerManager.class) {
                playerManager = new PlayerManager(context);
            }
        }
        return playerManager;
    }

    private PlayerManager(Context context) {
        this.context = context;
        mediaPlayer = new MediaPlayer();
        initAudioManager();
    }

    /**
     * 初始化音频管理器
     */
    private void initAudioManager() {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        } else {
            audioManager.setMode(AudioManager.MODE_IN_CALL);
        }
        audioManager.setSpeakerphoneOn(true);			//默认为扬声器播放
    }
    /**
     * 获取当前播放模式
     * @return
     */
    public int getCurrentMode() {
        return currentMode;
    }

    /**
     * 切换到耳机模式
     */
    public void changeToHeadsetMode(){
        currentMode = MODE_HEADSET;
        audioManager.setSpeakerphoneOn(false);
    }

    /**
     * 切换到听筒模式
     */
    public void changeToEarpieceMode(){
        currentMode = MODE_EARPIECE;
        audioManager.setSpeakerphoneOn(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    audioManager.getStreamMaxVolume(AudioManager.MODE_IN_COMMUNICATION), AudioManager.FX_KEY_CLICK);
        } else {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                    audioManager.getStreamMaxVolume(AudioManager.MODE_IN_CALL), AudioManager.FX_KEY_CLICK);
        }
    }
    /**
     * 切换到外放模式
     */
    public void changeToSpeakerMode(){
        currentMode = MODE_SPEAKER;
        audioManager.setSpeakerphoneOn(true);
    }
    public void resetPlayMode(){
        if (audioManager.isWiredHeadsetOn()){
            changeToHeadsetMode();
        } else {
            changeToSpeakerMode();
        }
    }
    /**
     * 播放回调接口
     */
    public interface PlayCallback {

        /**
         * 音乐准备完毕
         */
        void onPrepared();

        /**
         * 音乐播放完成
         */
        void onComplete();

        /**
         * 音乐停止播放
         */
        void onStop();
    }

    /**
     * 播放音乐
     *
     * @param path     音乐文件路径
     * @param callback 播放回调函数
     */
    public void play(String path, final PlayCallback callback) {
        if (!path.equals(this.filePath) && isPlaying()) {
            stop();
        }
        this.filePath = path;
        this.callback = callback;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(context, Uri.parse(path));
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    callback.onPrepared();
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    callback.onComplete();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始录音
     */
    public void recordVoice() {
        try {
            voiceFileName = context.getFilesDir().getAbsolutePath() + File.separator + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".amr";
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(voiceFileName);
            recorder.prepare();
            recorder.start();
            updateMicStatus();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 结束录音并获取录音文件路径
     *
     * @return
     * @throws Exception
     */
    public String getVoiceAmrPath() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
        if (voiceFileName != null) {
            return voiceFileName;
        }
        return null;
    }

    /**
     * 结束录音并获取录音文件路径
     *
     * @return
     * @throws Exception
     */
    public File getVoiceAmrFile() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
        if (voiceFileName != null) {
            File file = new File(voiceFileName);
            return file;
        }
        return null;
    }

    /**
     * 结束录音并获取录音文件路径
     *
     * @return
     * @throws Exception
     */
    public Uri getVoiceAmrUri() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
        if (voiceFileName != null) {
            File file = new File(voiceFileName);
            Uri uri = Uri.fromFile(file);
            return uri;
        }
        return null;
    }

    /**
     * 停止录音
     */
    public void stopRecord() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }

    /**
     * 获取录音文件时长
     *
     * @return
     */
    public int getVoiceDuration() {
        try {
            if (voiceFileName != null) {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(context, Uri.parse(voiceFileName));
                mediaPlayer.prepare();
                L.d(voiceFileName + " , " + mediaPlayer.getDuration());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer.getDuration() / 1000;
    }

    /**
     * 更新话筒状态
     */
    private int BASE = 1;
    private int SPACE = 100;// 间隔取样时间

    private void updateMicStatus() {
        if (recorder != null) {
            double ratio = (double) recorder.getMaxAmplitude() / BASE;
            double db = 0;// 分贝
            if (ratio > 1)
                db = 20 * Math.log10(ratio);
//            Log.d("GeekBean", "分贝值：" + db);

            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
//            mHandler.sendEmptyMessage((int) Math.round(db / 100 * 36));//音波高度
            mHandler.sendEmptyMessage((int) Math.round(db));
        }
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (onVoiceRecordingListener != null) {
                onVoiceRecordingListener.onWaveChange(msg.what);
            }

        }
    };
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };


    /**
     * 停止播放
     */
    public void stop() {
        if (isPlaying()) {
            try {
                mediaPlayer.stop();
                callback.onStop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 是否正在播放
     *
     * @return 正在播放返回true, 否则返回false
     */
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public interface OnVoiceRecordingListener {
        void onWaveChange(int wave);
    }

    public void setOnVoiceRecordingListener(OnVoiceRecordingListener onVoiceRecordingListener) {
        this.onVoiceRecordingListener = onVoiceRecordingListener;
    }
}
