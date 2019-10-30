package com.example.qsl.fragment.chat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpanStringUtils {
    public static SpannableString getEmotionContent(int emotion_map_type, Context context, TextView tv, String source) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();
        String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);
        while (matcherEmotion.find()) {
            String key = matcherEmotion.group();
            int start = matcherEmotion.start();
            Integer imgRes = Integer.valueOf(EmotionUtils.getImgByName(emotion_map_type, key));
            if (imgRes != null) {
                int size = (((int) tv.getTextSize()) * 13) / 10;
                spannableString.setSpan(new ImageSpan(context, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(res, imgRes.intValue()), size, size, true)), start, key.length() + start, 33);
            }
        }
        return spannableString;
    }
}
