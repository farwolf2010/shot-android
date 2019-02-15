package com.farwolf.shot.module;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import com.farwolf.util.Picture;
import com.farwolf.util.SDCard;
import com.farwolf.weex.annotation.WeexModule;
import com.farwolf.weex.base.WXModuleBase;
import com.farwolf.weex.util.Const;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.ui.component.WXComponent;

import java.io.File;
import java.util.HashMap;

@WeexModule(name="shot")
public class WXShotModule extends WXModuleBase {


    @JSMethod
    public void shot(String id, JSCallback callback){
        WXComponent com= findComponent(id);
        Bitmap bitmap= viewConversionBitmap(com.getHostView());
//        Bitmap bitmap= viewConversionBitmap(((WeexActivity)getActivity()).root);
        String dir=SDCard.getBasePath(getContext())+"/shot/";
        File f=new File(dir);
        if(f.exists()){
            f.delete();
        }
        f.mkdirs();
        Picture.saveImageToSDCard( dir+"shot.png",bitmap);
        HashMap m=new HashMap();
        m.put("path", Const.PREFIX_SDCARD+dir+"shot.png");
        callback.invoke(m);

    }

    public Bitmap viewConversionBitmap(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        if(w==0||h==0)
        {
            w=1;
            h=1;
        }
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }
}
