package com.cea.celibrary.utils;

import android.content.Context;
import android.os.Build;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class AndroidID {

    /**
     * 获取Android 设备唯一标识
     * @return	String
     * 				Android 设备唯一标识
     */
    public static String getAndroidID(Context context){
        //1,获取 IMEI
//       TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//   	String m_szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE

        //2 获取 DEVICE ID
        String m_szDevIDShort = "35" + //将DEVICE ID格式化成 IMEI
                Build.BOARD.length()%10+ Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
                Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 + Build.TYPE.length()%10 +
                Build.USER.length()%10 ; //13 digits

        //3 android ID
//       String m_szAndroidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);

        //4 wifi manager, read MAC address - requires  android.permission.ACCESS_WIFI_STATE or comes as null
//       WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
//       String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();

//       //5蓝牙MAC地址获取  必须加入权限 android.permission.BLUETOOTH required
//       BluetoothAdapter m_BluetoothAdapter	= null; // Local Bluetooth adapter
//   	m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//   	String m_szBTMAC =null;
//   	if(m_BluetoothAdapter!=null){	//有蓝牙设备则输出mac
//   		 m_szBTMAC = m_BluetoothAdapter.getAddress();
//   	}else{
//   		 m_szBTMAC =null;
//   	}


        //6 拼接以上5种设备ID后计算出MD5值并返回
        String m_szLongID = getMac()==null?m_szDevIDShort:getMac() ;	//拼接
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(),0,m_szLongID.length());
        byte p_md5Data[] = m.digest();

        String m_szUniqueID = new String();
        for (int i=0;i<p_md5Data.length;i++) {
            int b =  (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF) m_szUniqueID+="0";
            // add number to string
            m_szUniqueID+=Integer.toHexString(b);
        }
        m_szUniqueID = m_szUniqueID.toUpperCase();

//       m_tv.setText("Android Unique Device ID\n\n\n\n"+
//
//       		"IMEI:> "+m_szImei+
//       		"\nDeviceID:> "+m_szDevIDShort+
//       		"\nAndroidID:> "+m_szAndroidID+
//       		"\nWLANMAC:> "+m_szWLANMAC+
//       		"\nBTMAC:> "+m_szBTMAC+
//       		"\n\nUNIQUE ID:>"+m_szUniqueID );
//       L.i("uuid------->"+getMac()+"-------->"+m_szUniqueID+"------MD5------>"+Md5.Md5_32_16(m_szUniqueID, true));
        return m_szUniqueID;	//返回计算出的MD5值

    }

    public static String getMac() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str;) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }

}
