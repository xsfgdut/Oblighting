package com.ob.obsmarthouse.common.util;


import com.ob.obsmarthouse.common.constant.OBConstant;

/**
 * 对于接收线程端的读取的秘钥处理，拿到当前Home页面setofcmd指向的实例，拿到密码；
 * 算法类
 */
public class Sbox {

    private static int[] sbox = {0x13, 0x51, 0x24, 0x67, 0xf1, 0xa9, 0x4b, 0x9c, 0xc8, 0x74, 0x62, 0x3d, 0xd0, 0x81, 0x7a, 0xe0};
    private static byte[] def = {0x38, 0x38, 0x38, 0x38, 0x38, 0x38, 0x38, 0x38};
    private static byte[] normal = {0x38, 0x38, 0x38, 0x38};
    private static byte[] obsdefault = {0x31, 0x30, 0x30, 0x30};
    private static byte[] obsmodify = {0x31, 0x30, 0x30, 0x31};
    private static byte[] head = new byte[4];
    private static byte[] cache = new byte[64];
    /**
     * 解密后数据
     */
    static byte[] result = new byte[64];
    /**
     * 发送数据
     */
    static byte[] goal = new byte[68];

    /**
     * 解密算法
     *
     * @param res 要解密的68位数据
     * @param key 当前连接的密码
     * @return 解密后的64位数据
     */
    public synchronized static byte[] unPack(byte[] res, byte[] key) {
        System.arraycopy(res, 0, head, 0, 4);
        System.arraycopy(res, 4, result, 0, 64);
        /*不运算*/
//        if (Arrays.equals(head, obsmodify) ){
//            obs(result, key);
//        }
//        else if( Arrays.equals(head, obsdefault)) {
//            obs(result, def);
//        }
        return result;
    }

    /**
     * 加密算法
     *
     * @param res            62位未携带校验码数据
     * @param key            当前连接的密码
     * @param encryptionType 加密类型 只有为0的时候表示不需要加密 ，1位默认密码加密，2为用户修改后密码加密
     * @return 加密后的68位数据
     */
    public synchronized static byte[] pack(byte[] res, byte[] key, int encryptionType) {
        int crc = CRC16(res, 62);
        switch (encryptionType) {
            case OBConstant.PackType.UNENCRYPTED:
                System.arraycopy(normal, 0, goal, 0, 4);
                break;
            case OBConstant.PackType.ORIGINAL_ENCRYPTED:
                System.arraycopy(obsdefault, 0, goal, 0, 4);
                break;
            case OBConstant.PackType.ACTIVATED_UNENCRYPTED:
                System.arraycopy(obsmodify, 0, goal, 0, 4);
                break;
        }
        System.arraycopy(res, 0, goal, 4, 62);
        goal[66] = (byte) (crc >> 8);
        goal[67] = (byte) crc;
        System.arraycopy(goal, 4, cache, 0, 64);
        //不使用加密算法
//        if (encryptionType == OBConstant.ACTIVATED_UNENCRYPTED) {
//            obs(cache, key);
//        }else if (encryptionType == OBConstant.ORIGINAL_ENCRYPTED) {
//            obs(cache, def);
//        }
        System.arraycopy(cache, 0, goal, 4, 64);
        return goal;
    }

    /**
     * @param result 加密数据
     * @param key
     */
    private static void obs(byte[] result, byte[] key) {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 8; i++) {
                int value = key[i] & 0xff;
                int high = (value >> 4);
                int low = value & 0x0f;
                int highGoal = sbox[high] ^ result[2 * i + 16 * j];
                int lowGoal = sbox[low] ^ result[2 * i + 16 * j + 1];
                result[2 * i + 16 * j] = (byte) highGoal;
                result[2 * i + 16 * j + 1] = (byte) lowGoal;
            }
        }
    }

    private static int calcByte(int crc, char b) {
        crc += ((b & 0x00ff) + (b >> 8 & 0xff00));
        return crc & 0xffff;
    }

    /**
     * crc校验
     * * @return 返回byte数组的crc校验值
     */
    public static int CRC16(byte[] pBuffer, int length) {
        int wCRC16 = 0;
        if ((pBuffer == null) || (length == 0)) {
            return 0;
        }
        for (int i = 0; i < length; i++) {
            wCRC16 = calcByte(wCRC16, (char) pBuffer[i]);
        }
        return wCRC16;
    }
}