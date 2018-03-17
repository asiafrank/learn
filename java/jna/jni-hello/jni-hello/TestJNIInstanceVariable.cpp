#include <jni.h>
#include <iostream>
using namespace std;

extern "C" 
{
    JNIEXPORT void JNICALL Java_com_asiafrank_jni_TestJNIInstanceVariable_modifyInstanceVariable
    (JNIEnv *env, jobject thisObj)
    {
        // Get a reference to this object's class
        jclass thisClass = env->GetObjectClass(thisObj);

        // int
        // Get the Field ID of the instance variables "number"
        jfieldID fidNumber = env->GetFieldID(thisClass, "number", "I");
        if (NULL == fidNumber) return;

        // Get the int given the Field ID
        jint number = env->GetIntField(thisObj, fidNumber);
        cout << "In C, the int is " << number << endl;

        // Change the variable
        number = 99;
        env->SetIntField(thisObj, fidNumber, number);

        // Get the Field ID of the instance variables "message"
        jfieldID fidMessage = env->GetFieldID(thisClass, "message", "Ljava/lang/String;");
        if (NULL == fidMessage) return;

        // String
        // Get the object given the Field ID
        jstring message = (jstring) env->GetObjectField(thisObj, fidMessage);

        // Create a C-string with the JNI String
        const char *cStr = env->GetStringUTFChars(message, NULL);
        if (NULL == cStr) return;

        cout << "In C, the string is " << cStr << endl;
        env->ReleaseStringUTFChars(message, cStr);

        // Create a new C-string and assign to the JNI string
        message = env->NewStringUTF("Hello from C");
        if (NULL == message) return;

        // modify the instance variables
        env->SetObjectField(thisObj, fidMessage, message);
    }
}
