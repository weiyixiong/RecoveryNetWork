package com.wyx.recoverynetwork;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * @author winney E-mail: 542111388@qq.com
 * @version 创建时间: 2015/11/10 上午10:25
 */
public class RecoveryNetWorkSignal implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("android"))
            return;
        findAndHookMethod("android.telephony.SignalStrength", lpparam.classLoader, "getLevel", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                XposedBridge.log("mSignalStrength: afterHookedMethod  " + param.getResult());
                param.setResult(1);
            }
        });
    }
}
