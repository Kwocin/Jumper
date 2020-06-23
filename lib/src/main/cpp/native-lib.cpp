#include <jni.h>
#include <string>

extern "C" {
extern int bspatch(int argc, const char *argv[]);
}
extern "C" JNIEXPORT jstring JNICALL
Java_ink_girigiri_bsdiff_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_ink_girigiri_lib_utils_InstallUtils_00024Companion_installApkForBspatch(JNIEnv *env,
                                                                             jobject thiz,
                                                                             jstring old_apk,
                                                                             jstring out_,
                                                                             jstring patch_) {

    const char *oldApk = env->GetStringUTFChars(old_apk, 0);
    const char *out = env->GetStringUTFChars(out_, 0);
    const char *patch = env->GetStringUTFChars(patch_, 0);


    const char *argv[] = {"bspatch", oldApk, out, patch};
    bspatch(4, argv);

    env->ReleaseStringUTFChars(old_apk, oldApk);
    env->ReleaseStringUTFChars(patch_, patch);
    env->ReleaseStringUTFChars(out_, out);

}