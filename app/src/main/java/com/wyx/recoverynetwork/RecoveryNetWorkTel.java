package com.wyx.recoverynetwork;

import android.telephony.SignalStrength;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getIntField;
import static de.robv.android.xposed.XposedHelpers.setIntField;

/**
 * @author winney E-mail: 542111388@qq.com
 * @version 创建时间: 2015/11/10 上午10:25
 */
public class RecoveryNetWorkTel implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("android"))
            return;
//        Method[] methods = findClass("com.android.server.TelephonyRegistry", lpparam.classLoader).getMethods();
//        for (Method method : methods) {
//            XposedBridge.log("methodName:   " + method.getName() + "paramName:" + method.getParameterTypes().getClass().getSimpleName());
//        }
        findAndHookMethod("com.android.server.TelephonyRegistry", lpparam.classLoader, "notifySignalStrength", SignalStrength.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                SignalStrength object = (SignalStrength) param.args[0];
//                XposedBridge.log("mSignalStrength" + object.toString());
                int signalStrength = getIntField(object, "mGsmSignalStrength");
                if (signalStrength <= 2 || signalStrength == 99) {
                    setIntField(object, "mGsmSignalStrength", 14);
                }
//                XposedBridge.log("mSignalStrength:   " + signalStrength);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {

            }
        });
    }
}
