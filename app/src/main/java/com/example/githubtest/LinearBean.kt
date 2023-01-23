package com.example.githubtest

import android.graphics.Paint
import android.graphics.Path

/**
 * 描画データをまとめて扱う
 */
data class LinearBean(
    /** パス情報 */
    val path : Path,
    /** ペイント情報(カラーや太さを含む) */
    val paint : Paint
)
