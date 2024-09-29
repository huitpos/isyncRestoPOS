#include <jni.h>
#include <string>
#include <iostream>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_isyncpos_NativeBridge_getAlgorithm(JNIEnv *env, jobject obj) {
    return env->NewStringUTF("Blowfish");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_isyncpos_NativeBridge_getMode(JNIEnv *env, jobject obj) {
    return env->NewStringUTF("Blowfish/CBC/PKCS5Padding");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_isyncpos_NativeBridge_getIV(JNIEnv *env, jobject obj) {
    return env->NewStringUTF("dJh6xItf");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_isyncpos_NativeBridge_getSecretKey(JNIEnv *env, jobject obj) {
    return env->NewStringUTF("zh0*i-^cpid+++tbuc*x+0zu$eu*59paz@19&$*z#(2)yc)x1_");
}