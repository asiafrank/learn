#include <jni.h>
#include <iostream>

using namespace std;

extern "C"
{
    JNIEXPORT void JNICALL Java_com_asiafrank_jni_TestJNICallBackMethod_nativeMethod
    (JNIEnv *env, jobject thisObj)
    {
        // Get a class reference for this object
        jclass thisClass = env->GetObjectClass(thisObj);

        // Get the Method ID for method "callback", which takes no arg and return void
        jmethodID midCallBack = env->GetMethodID(thisClass, "callback", "()V");
        if (NULL == midCallBack) return;
        cout << "In C, call back Java's callback()" << endl;
        // Call back the method (which returns void), based on the Method ID
        env->CallVoidMethod(thisObj, midCallBack);

        jmethodID midCallBackStr = env->GetMethodID(thisClass, "callback", "(Ljava/lang/String;)V");
        if (NULL == midCallBackStr) return;
        cout << "In C, call back Java's called(String)" << endl;
        jstring message = env->NewStringUTF("Hello from C");
        env->CallVoidMethod(thisObj, midCallBackStr, message);

        jmethodID midCallBackAverage = env->GetMethodID(thisClass, "callbackAverage", "(II)D");
        if (NULL == midCallBackAverage) return;
        jdouble average = env->CallDoubleMethod(thisObj, midCallBackAverage, 2, 3);
        cout << "In C, the average is " << average << endl;

        jmethodID midCallBackStatic = env->GetStaticMethodID(thisClass, "callbackStatic", "()Ljava/lang/String;");
        if (NULL == midCallBackStatic) return;
        jstring resultJNIStr = (jstring)env->CallStaticObjectMethod(thisClass, midCallBackStatic);
        const char *resultCStr = env->GetStringUTFChars(resultJNIStr, NULL);
        if (NULL == resultCStr) return;
        cout << "In C, the returned string is " << resultCStr << endl;
        env->ReleaseStringUTFChars(resultJNIStr, resultCStr);
    }
}