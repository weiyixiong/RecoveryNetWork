package com.wyx.recoverynetwork;

import android.os.Build;
import android.telephony.SignalStrength;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getIntField;
import static de.robv.android.xposed.XposedHelpers.getObjectField;
import static de.robv.android.xposed.XposedHelpers.setIntField;

/**
 * @author winney E-mail: 542111388@qq.com
 * @version 创建时间: 2015/11/10 上午10:25
 */
public class RecoveryNetWorkUI implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        //        XposedBridge.log("Loaded app: " + lpparam.packageName);
        String networkClassName = "com.android.systemui.statusbar.policy.NetworkController";
        if (Build.VERSION.SDK_INT > 20) {
            networkClassName = "com.android.systemui.statusbar.policy.NetworkControllerImpl";
        }
        if (!lpparam.packageName.equals("com.android.systemui"))
            return;
        findAndHookMethod(networkClassName, lpparam.classLoader, "hasService", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
        findAndHookMethod(networkClassName, lpparam.classLoader, "updateTelephonySignalStrength", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                SignalStrength object = (SignalStrength) getObjectField(param.thisObject, "mSignalStrength");
                if (object == null) {
                    XposedBridge.log("mSignalStrength:mSignalStrength==null");
                    return;
                }
                int signalStrength = getIntField(object, "mGsmSignalStrength");
                if (signalStrength <= 2 || signalStrength == 99) {
                    setIntField(object, "mGsmSignalStrength", 14);
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            }
        });

//        findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                // this will be called before the clock was updated by the original method
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                TextView tv = (TextView) param.thisObject;
//                String text = tv.getText().toString();
//                tv.setText(text + " :)");
//                tv.setTextColor(Color.RED);
//            }
//        });
    }
}
