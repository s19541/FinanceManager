package com.example.financemanager

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

class GraphView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var maxY = 0f
    var maxX = 0
    private var selectedMonth = MainActivity.selectedMonth
    val sdf = SimpleDateFormat("MMM yyyy")
    var transactions = Shared.transactionList.sortedBy{ it.date }.filter{sdf.format(it.date).equals(sdf.format(selectedMonth))}

    fun changeMonth(){
        transactions = Shared.transactionList.sortedBy{ it.date }.filter{sdf.format(it.date).equals(sdf.format(selectedMonth))}

    }
     override fun onDraw(canvas : Canvas) {
         super.onDraw(canvas);
         if(transactions == null)
         {
            return;
         }
         val graphValues = createGraphValues()
         createGraphValues()
         findMinMax(graphValues)


         var paint = Paint()
         paint.color = Color.GRAY
         paint.strokeWidth = 10f
         canvas?.drawLine(150f, height/2f, width.toFloat(), height/2f, paint)
         paint.color = Color.BLACK
         paint.strokeWidth = 5f
         canvas?.drawLine(150f, 0f, 150f, height.toFloat(), paint)

         paint.textSize = 20f
        for(i in 1..maxX){
            canvas?.drawLine((width-180f)/maxX * i + 150f, height/2f, (width-180f)/maxX * i + 150f, height/2f -15f  , paint)
            canvas?.drawText(i.toString(), (width-180f)/maxX * i + 145f, height/2f + 30f, paint)
        }

         paint.textSize = 30f
         canvas?.drawText(maxY.toString() + " pln",5f,30f,paint)
         canvas?.drawText((maxY/2).toString() + " pln",5f,height/4+30f,paint)
         canvas?.drawText(0f.toString() + " pln",5f,height/2f +10,paint)
         canvas?.drawText((-maxY).toString() + " pln",5f,height - 10f,paint)
         canvas?.drawText((maxY/-2).toString() + " pln",5f,height-(height/4f + 10f),paint)

         var previousKey = 0
         var previousValue = 0f
         for((key,value) in graphValues){
             val startX = (width-180f)/maxX * previousKey + 150f
             val startY = height/2 - (height/2)/maxY*previousValue
             val stopX = (width-180f)/maxX * key + 150f
             val stopY = height/2 - (height/2)/maxY*value
             if(previousValue*value>0) {
                 if (value > 0)
                     paint.color = Color.GREEN
                 else
                     paint.color = Color.RED

                 canvas?.drawLine(startX, startY, stopX, stopY, paint)
             }
             else{
                 val middleX = ((height/2 - startY)*(stopX-startX))/(stopY-startY) + startX
                 canvas?.drawLine(startX, startY, middleX, height/2f, paint)
                 if(value>0)
                     paint.color = Color.GREEN
                 else
                     paint.color = Color.RED
                 canvas?.drawLine(middleX, height/2f, stopX, stopY, paint)
             }
             previousKey = key
             previousValue = value
         }
         canvas?.drawLine((width-180f)/maxX * previousKey + 150f,height/2 - (height/2)/maxY*previousValue,(width-180f)/maxX * 30 + 150f,height/2 - (height/2)/maxY*previousValue,paint)


        /*int maxX = m_graphArray.length;

        float factorX = getWidth() / (float)maxX;
        float factorY = getHeight() / (float)m_maxY;

        for(int i = 1; i < m_graphArray.length; ++i) {
            int x0 = i - 1;
            int y0 = m_graphArray[i-1];
            int x1 = i;
            int y1 = m_graphArray[i];

            int sx = (int)(x0 * factorX);
            int sy = getHeight() - (int)(y0* factorY);
            int ex = (int)(x1*factorX);
            int ey = getHeight() - (int)(y1* factorY);
            canvas.drawLine(sx, sy, ex, ey, m_paint);*/
        }
    private fun createGraphValues(): MutableMap<Int,Float>{
        var map : MutableMap<Int, Float> = mutableMapOf()
        var balance = 0f
        for(transaction in transactions){
            var cal = Calendar.getInstance()
            cal.time = transaction.date
            balance += transaction.amount
            balance = Math.round(balance*100f)/100f
            map.put(cal.get(Calendar.DAY_OF_MONTH),balance)
        }
        return map
    }
    private fun findMinMax(map: MutableMap<Int, Float>){
        var cal = Calendar.getInstance()
        cal.time = selectedMonth
        maxX = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        for( (key,value) in map){
            if(value>maxY)
                maxY = value
            if(value*-1>maxY)
                maxY = value*-1
        }
    }
}