#include <stdio.h>
#include <stdlib.h>
#include <elian.h>
#include "elianjni.h"

//#define ENCKEY "McdwCnwCdss2_18p"

static void *context = NULL;

JNIEXPORT jint JNICALL Java_com_mediatek_elian_ElianNative_GetProtoVersion
  (JNIEnv *, jobject)
{
	int protoVersion = 0;
	int libVersion = 0;

	elianGetVersion(&protoVersion, &libVersion);
	return protoVersion;
}

JNIEXPORT jint JNICALL Java_com_mediatek_elian_ElianNative_GetLibVersion
  (JNIEnv *, jobject)
{
	int protoVersion = 0;
	int libVersion = 0;

	elianGetVersion(&protoVersion, &libVersion);
	return libVersion;
}

JNIEXPORT jint JNICALL Java_com_mediatek_elian_ElianNative_InitSmartConnection
  (JNIEnv *, jobject, jstring, jint sendV1, jint sendV4)
{
    unsigned char target[] = {0xff, 0xff, 0xff, 0xff, 0xff, 0xff};
    unsigned int flag = 0;

    if (context)
    {
	    elianStop(context);
	    elianDestroy(context);
	    context = NULL;
    }

    if (sendV1)
    {
    	flag |= ELIAN_SEND_V1;
    }
    if (sendV4)
    {
    	flag |= ELIAN_SEND_V4;
    }
    context = elianNew(NULL, 0, target, flag);
    if (context == NULL)
    {
        return -1;
    }
    return 0;
}

/*
 * Class:     com_mediatek_elian_ElianNative
 * Method:    StartSmartConnection
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;B)I
 */
JNIEXPORT jint JNICALL Java_com_mediatek_elian_ElianNative_StartSmartConnection
  (JNIEnv *env, jobject, jstring SSID, jstring PASSWORD, jstring CUSTOM)
{
    const char *ssid = NULL;
    const char *password = NULL;
    const char *custom = NULL;

    if (context == NULL)
    {
    	return -1;
    }
    ssid = env->GetStringUTFChars(SSID, 0);
    password = env->GetStringUTFChars(PASSWORD, 0);
    custom = env->GetStringUTFChars(CUSTOM, 0);

//    elianPut(context, TYPE_ID_AM, (char *)&authmode, 1);
    elianPut(context, TYPE_ID_SSID, (char *)ssid, strlen(ssid));
    elianPut(context, TYPE_ID_PWD, (char *)password, strlen(password));
    elianPut(context, TYPE_ID_CUST, (char *)custom, strlen(custom));

    env->ReleaseStringUTFChars(SSID, ssid);
    env->ReleaseStringUTFChars(PASSWORD, password);
    env->ReleaseStringUTFChars(CUSTOM, custom);

    elianStart(context);

    return 0;
}

/*
 * Class:     com_mediatek_elian_ElianNative
 * Method:    StopSmartConnection
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_mediatek_elian_ElianNative_StopSmartConnection
  (JNIEnv *, jobject)
{
	if (context)
	{
	    elianStop(context);
	    elianDestroy(context);
	    context = NULL;
	}
    return 0;
}
