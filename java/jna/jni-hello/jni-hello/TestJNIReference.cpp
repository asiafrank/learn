#include <jni.h>
#include <iostream>

using namespace std;

extern "C"
{
    JNIEXPORT jobject JNICALL Java_com_asiafrank_jni_TestJNIReference_getIntegerObject
    (JNIEnv *env, jobject thisObj, jint number);

    JNIEXPORT jobject JNICALL Java_com_asiafrank_jni_TestJNIReference_anotherGetIntegerObject
    (JNIEnv *env, jobject thisObj, jint number);
}

// Global Reference to the Java class "java.lang.Integer"
static jclass classInteger;
static jmethodID midIntegerInit;

jobject getInteger(JNIEnv *env, jobject thisObj, jint number)
{
    // Get a class reference for java.lang.Integer if missing
    if (NULL == classInteger)
    {
        cout << "Find java.lang.Integer" << endl;
        // FindClass returns a local reference
        jclass classIntegerLocal = env->FindClass("java/lang/Integer");
        // Create a global reference from the local reference
        classInteger = (jclass)env->NewGlobalRef(classIntegerLocal);
        // No longer need the local reference, free it!
        env->DeleteLocalRef(classIntegerLocal);
    }
    if (NULL == classInteger) return NULL;

    // Get the Method ID of the Integer's constructor if missing
    if (NULL == midIntegerInit)
    {
        cout << "Get Method ID for java.lang.Integer's constructor" << endl;
        midIntegerInit = env->GetMethodID(classInteger, "<init>", "(I)V");
    }
    if (NULL == midIntegerInit) return NULL;

    // Call back constructor to allocate a new instance, with an int argument
    jobject newObj = env->NewObject(classInteger, midIntegerInit, number);
    cout << "In C, constructed java.lang.Integer with number " << number << endl;
    return newObj;
}

JNIEXPORT jobject JNICALL Java_com_asiafrank_jni_TestJNIReference_getIntegerObject
(JNIEnv * env, jobject thisObj, jint number)
{
    return getInteger(env, thisObj, number);
}

JNIEXPORT jobject JNICALL Java_com_asiafrank_jni_TestJNIReference_anotherGetIntegerObject
(JNIEnv * env, jobject thisObj, jint number)
{
    return getInteger(env, thisObj, number);
}
