package es.source.code.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import es.source.code.service.UpdateService;

public class DeviceStartedListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyReceiver","onReceive方法被执行");
        // 实现开机启动
        if (intent.getAction().equals("android.media.AUDIO_BECOMING_NOISY") || intent.getAction().equals("Intent.ACTION_BOOT_COMPLETED")) {
            /* 服务开机自启动 */
            Intent service = new Intent(context, UpdateService.class);
            context.startService(service);
         }


//        Intent bootIntent = new Intent(context, UpdateService.class);
//        // 需要设置标记不然会报错
//        bootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        // 开启
//        context.startActivity(bootIntent);
    }
}
