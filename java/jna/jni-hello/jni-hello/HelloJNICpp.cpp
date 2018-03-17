#include <jni.h>
#include "HelloJNICpp.h"
#include "HelloJNICppImpl.h"

/*
c mix c++
*/
JNIEXPORT void JNICALL Java_com_asiafrank_jni_HelloJNICpp_sayHello(JNIEnv *, jobject)
{
    sayHello();
    return;
}
