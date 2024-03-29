package com.example.githubtest

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path

class Linears {

    public var counter = 0
    var linears: ArrayDeque<LinearBean> = ArrayDeque()

    public fun isEmpty(): Boolean {
        return this.linears.isEmpty()
    }

    public fun add(item: LinearBean) {
        this.linears.addLast(item)
    }

    public fun add(path: Path,paint: Paint) {
        this.linears.addLast(LinearBean(path, paint))
    }

    public fun add(path: Path, color: Int, pensize: Float) {
        val paint :Paint = Paint()
        paint!!.color = color
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeCap = Paint.Cap.ROUND
        paint!!.isAntiAlias = true
        paint!!.strokeWidth = pensize
        this.linears.addLast(LinearBean(path, paint))
    }

    public fun last() : LinearBean {
        return linears.last()
    }

    public fun remove() {
        this.linears.removeLast()
    }

    public fun removeAll() {
        this.linears.removeAll(linears)
    }

    public fun limitLast(): Int {
        return this.linears.size - this.counter
    }

    public fun redraw() :ArrayDeque<LinearBean> {
        counter++
        return this.linears
    }

    public fun getsize() :Int{
        return linears.size
    }

}

public fun Linears.getLinears(): ArrayDeque<LinearBean> {
    return this.linears
}
