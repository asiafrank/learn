#include <jni.h>
#include <iostream>

using namespace std;

extern "C"
{
    JNIEXPORT void JNICALL Java_com_asiafrank_jni_TestJNIStaticVariable_modifyStaticVariable
    (JNIEnv *env, jobject thisObj)
    {
        // Get a reference to this object's class
        jclass cls = env->GetObjectClass(thisObj);

        // Read the int static variable and modify its value
        jfieldID fidNumber = env->GetStaticFieldID(cls, "number", "D");
        if (NULL == fidNumber) return;

        jdouble number = env->GetStaticDoubleField(cls, fidNumber);
        cout << "In C, the double is " << number << endl;

        number = 77.88;
        env->SetStaticDoubleField(cls, fidNumber, number);
    }
}