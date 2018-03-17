#include <jni.h>

extern "C" {
    JNIEXPORT jdoubleArray JNICALL Java_com_asiafrank_jni_TestJNIPrimitiveArray_sumAndAverage
    (JNIEnv *env, jobject thisObj, jintArray inJNIArray)
    {
        // Step 1: Convert the incoming JNI jintarray to C's jint[]
        jint *inCArray = env->GetIntArrayElements(inJNIArray, NULL);
        if (NULL == inCArray) return NULL;
        jsize length = env->GetArrayLength(inJNIArray);

        // Step 2: Perform its intended operations
        jint sum = 0;
        int i;
        for (i = 0; i < length; i++)
        {
            sum += inCArray[i];
        }

        jdouble average = (jdouble)sum / length;
        env->ReleaseIntArrayElements(inJNIArray, inCArray, 0); // release resources

        jdouble outCArray[] = {sum, average};

        // Step 3: Convert the C's Native jdouble[] to JNI jdoublearray, and return
        jdoubleArray outJNIArray = env->NewDoubleArray(2); // allocate
        if (NULL == outJNIArray) return NULL;
        env->SetDoubleArrayRegion(outJNIArray, 0, 2, outCArray);
        return outJNIArray;
    }
}
