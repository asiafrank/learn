#include <jni.h>
#include <iostream>
using namespace std;

// 使用 VC++ compiler 编译 JNI C++ 代码，使用 extern "C" 包裹 jni 方法才有效
// extern C 作用见 https://stackoverflow.com/questions/1041866/in-c-source-what-is-the-effect-of-extern-c
extern "C" {
    JNIEXPORT jdouble JNICALL Java_com_asiafrank_jni_TestJNIPrimitive_average(JNIEnv *env, jobject obj, jint n1, jint n2)
    {
        jdouble result;
        cout << "In C++, the numbers are " << n1 << " and " << n2 << endl;
        result = ((jdouble)n1 + n2) / 2.0;
        // jint is mapped to int, jdouble is mapped to double
        return result;
    }
}