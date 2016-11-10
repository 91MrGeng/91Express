package com.cea.celibrary.customview.Text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;

import com.cea.celibrary.R;
import com.cea.celibrary.emoji.EmojiconHandler;

import pl.droidsonroids.gif.GifTextView;


/**
 * 实现了 gif，图片，文字混排
 */
public class GifSpanTextView extends GifTextView {

    private int mEmojiconSize;
    private int mEmojiconAlignment;
    private int mEmojiconTextSize;
    private int mTextStart = 0;
    private int mTextLength = -1;
    private boolean mUseSystemDefault = false;


    private GifSpanChangeWatcher mGifSpanChangeWatcher;
    public GifSpanTextView(Context context) {
        super(context);
//        initGifSpanChangeWatcher(this);
    }

    public GifSpanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGifSpanChangeWatcher(attrs);
    }

    public GifSpanTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGifSpanChangeWatcher(attrs);
    }

    private void initGifSpanChangeWatcher(AttributeSet attrs) {
        mGifSpanChangeWatcher = new GifSpanChangeWatcher(this);
        addTextChangedListener(mGifSpanChangeWatcher);

        mEmojiconTextSize = (int) getTextSize();
        if (attrs == null) {
            mEmojiconSize = (int) getTextSize();
        } else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Emojicon);
            mEmojiconSize = (int) a.getDimension(R.styleable.Emojicon_emojiconSize, getTextSize());
            mEmojiconAlignment = a.getInt(R.styleable.Emojicon_emojiconAlignment, DynamicDrawableSpan.ALIGN_BASELINE);
            mTextStart = a.getInteger(R.styleable.Emojicon_emojiconTextStart, 0);
            mTextLength = a.getInteger(R.styleable.Emojicon_emojiconTextLength, -1);
            mUseSystemDefault = a.getBoolean(R.styleable.Emojicon_emojiconUseSystemDefault, false);
            a.recycle();
        }
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        type = BufferType.EDITABLE;
        CharSequence oldText = getText();
        if (!TextUtils.isEmpty(oldText) && oldText instanceof Spannable) {
            Spannable sp = (Spannable) oldText;
            final GifImageSpan[] spans = sp.getSpans(0, sp.length(), GifImageSpan.class);
            final int count = spans.length;
            for (int i = 0; i < count; i++) {
                spans[i].getDrawable().setCallback(null);
            }

            final GifSpanChangeWatcher[] watchers = sp.getSpans(0, sp.length(), GifSpanChangeWatcher.class);
            final int count1 = watchers.length;
            for (int i = 0; i < count1; i++) {
                sp.removeSpan(watchers[i]);
            }
        }

        if (!TextUtils.isEmpty(text) && text instanceof Spannable) {
            Spannable sp = (Spannable) text;
            final GifImageSpan[] spans = sp.getSpans(0, sp.length(), GifImageSpan.class);
            final int count = spans.length;
            for (int i = 0; i < count; i++) {
                spans[i].getDrawable().setCallback(this);
            }

            final GifSpanChangeWatcher[] watchers = sp.getSpans(0, sp.length(), GifSpanChangeWatcher.class);
            final int count1 = watchers.length;
            for (int i = 0; i < count1; i++) {
                sp.removeSpan(watchers[i]);
            }

            if (mGifSpanChangeWatcher == null) {
                mGifSpanChangeWatcher = new GifSpanChangeWatcher(this);;
            }

            sp.setSpan(mGifSpanChangeWatcher, 0, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE | (100 << Spanned.SPAN_PRIORITY_SHIFT));
        }

        if (!TextUtils.isEmpty(text)) {
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            EmojiconHandler.addEmojis(getContext(), builder, mEmojiconSize, mEmojiconAlignment, mEmojiconTextSize, mTextStart, mTextLength, mUseSystemDefault);
            text = builder;
        }

        super.setText(text, type);
    }

    private GifImageSpan getImageSpan(Drawable drawable) {
        GifImageSpan imageSpan = null;
        CharSequence text = getText();
        if (!TextUtils.isEmpty(text)) {
            if (text instanceof Spanned) {
                Spanned spanned = (Spanned) text;
                GifImageSpan[] spans = spanned.getSpans(0, text.length(), GifImageSpan.class);
                if (spans != null && spans.length > 0) {
                    for (GifImageSpan span : spans) {
                        if (drawable == span.getDrawable()) {
                            imageSpan = span;
                        }
                    }
                }
            }
        }

        return imageSpan;
    }

    @Override
    public void invalidateDrawable(Drawable drawable) {
        GifImageSpan imageSpan = getImageSpan(drawable);
        if (imageSpan != null) {
            CharSequence text = getText();
            if (!TextUtils.isEmpty(text)) {
                if (text instanceof Editable) {
                    Editable editable = (Editable)text;
                    int start = editable.getSpanStart(imageSpan);
                    int end = editable.getSpanEnd(imageSpan);
                    int flags = editable.getSpanFlags(imageSpan);

                    editable.removeSpan(imageSpan);
                    editable.setSpan(imageSpan, start, end, flags);
                }
            }

        } else {
            super.invalidateDrawable(drawable);
        }
    }

    /**
     * Set the size of emojicon in pixels.
     */
    public void setEmojiconSize(int pixels) {
        mEmojiconSize = pixels;
        super.setText(getText());
    }

    /**
     * Set whether to use system default emojicon
     */
    public void setUseSystemDefault(boolean useSystemDefault) {
        mUseSystemDefault = useSystemDefault;
    }
}
