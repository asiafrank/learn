#include <jni.h>
#include <iostream>

using namespace std;

extern "C"
{
    JNIEXPORT jobject JNICALL Java_com_asiafrank_jni_TestJNIConstructor_getIntegerObject
    (JNIEnv *env, jobject thisObj, jint number)
    {
        // Get a class reference for java.lang.Integer
        jclass cls = env->FindClass("java/lang/Integer");

        // Get the Method ID of the constructor which takes an int
        jmethodID midInit = env->GetMethodID(cls, "<init>", "(I)V");
        if (NULL == midInit) return NULL;
        // Call back constructor to allocate a new instance, with an int argument
        jobject newObj = env->NewObject(cls, midInit, number);

        // Try running the toString() on this newly create objecct
        jmethodID midToString = env->GetMethodID(cls, "toString", "()Ljava/lang/String;");
        if (NULL == midToString) return NULL;
        jstring resultStr = (jstring)env->CallObjectMethod(newObj, midToString);
        const char *resultCStr = env->GetStringUTFChars(resultStr, NULL);
        cout << "In C: the number is " << resultCStr << endl;

        return newObj;
    }
}