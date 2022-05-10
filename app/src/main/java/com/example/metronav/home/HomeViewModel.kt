package com.example.metronav.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.metronav.Station
import java.lang.Math.abs

class HomeViewModel() :ViewModel(){
    private val line1: Array<Station> = arrayOf(Station("Helwan","1"), Station("Ain Helwan","1"), Station("Helwan University","1"),
        Station("Wadi Hof","1"), Station("Hadayek Helwan","1"), Station("Elmasraa","1"),
        Station("Tura El-Esmant","1"), Station("Kozzika","1"), Station("Tora El-Balad","1"),
        Station("Sakanat El-Maadi","1"), Station("Maadi","1"), Station("Hadayek El-Maadi","1"),
        Station("Dar El-Salam","1"), Station("El-Zahraa","1"), Station("Mar Girgis","1"),
        Station("El-Malek El-Saleh","1"), Station("Al-Sayeda Zeinab","1"), Station("Saad Zaghloul","1"),
        Station("Sadat","T12"), Station("Nasser","T13"), Station("Orabi","1"),Station("Al-Shohadaa","1"),
        Station("Ghamra","1"), Station("El-Demerdash","1"), Station("Manshiet El-Sadr","1"),
        Station("Kobri El-Qobba","1"), Station("Hammamat El-Qobba","1"), Station("Saray El-Qobba","1"),
        Station("Hadayeq El-Zaitoun","1"), Station("Helmeyet El-Zaitoun","1"), Station("El-Matareyya","1"),
        Station("Ain Shams","1"), Station("Ezbet El-Nakhl","1"), Station("El-Marg","1"), Station("New Marg","1"))

    private val line2: Array<Station> = arrayOf(Station("El Monib","2"), Station("Sakiat Mekky","2"), Station("Omm El-Masryeen","2"),
        Station("Giza","2"), Station("Faisal","2"), Station("Cairo University","2"),
        Station("El Bohoth","2"), Station("Dokki","2"), Station("Opera","2"),
        Station("Sadat","T12"), Station("Mohamed Naguib","2"), Station("Attaba","T23"),
        Station("Al-Shohadaa","2"), Station("Masarra","2"), Station("Rod El-Farag","2"),
        Station("St. Teresa","2"), Station("Khalafawy","2"), Station("Mezallat","2"),
        Station("Koliet El-Zeraa","2"), Station("Shubra Al Khaimah","2"))

    private val line3: Array<Station> = arrayOf(Station("Rod El Farag Corridor","3"), Station("Ring Road","3"), Station("El-Qawmia","3"),
        Station("El-Bohy","3"), Station("Imbaba","3"), Station("Sudan","3"),
        Station("Kit-Kat","3"), Station("Safaa Hijazy","3"), Station("Maspero","3"),
        Station("Nasser","T13"), Station("Attaba","T23"), Station("Bab El Shaariya","3"),
        Station("El-Geish","3"), Station("Abdou Pasha","3"), Station("Abbassiya","3"),
        Station("Fair Zone","3"), Station("Stadium","3"), Station("Koleyet El-Banat","3"),
        Station("Al-Ahram","3"), Station("Haroun","3"), Station("Heliopolis","3"), Station("Alf Maskan","3"),
        Station("El Shams Club","3"), Station("El Nozha","3"), Station("Hesham Barakat","3"), Station("Qubaa","3"),
        Station("Omar Ibn El Khattab","3"), Station("Haykestep","3"), Station("Adly Mansour","3"))

    val stations:Array<Station> = line1.plus(line2.plus(line3))
    var from:String = ""
    var to:String = ""


    var linePoints:Array<Int> = arrayOf(0,0,0,0,0,0)
    var ts:Array<Int> = arrayOf(0,0,0,0,0,0)
    private val ticketPrices: Array<Int> = arrayOf(3,5,7)

    val _result = MutableLiveData<String>()
    val result:LiveData<String>
        get() = _result

    var fromStationPos = 0
    var toStationPos = 0
    var froml = 0
    var tol = 0
    var fromIndex = 0
    var toIndex = 0
    var min = 9999999
//    lateinit var from:String
//    lateinit var to:String

    init {
        Log.i("HomeViewModel","$from || $to")
    }

    private fun printTs(route:Array<Int>)
    {
        for((index,item) in route.withIndex())
        {
            if(index==0)
            {
                print("[ $item, ")
            }
            else if(index>0 && index< route.size-1)
            {
                print("$item, ")
            }
            else
            {
                print("$item ]")
            }
        }
        println()
    }

    private fun printRoute(route:Array<String>, ticketPrices:Array<Int>):String
    {
        var stringBuilder = StringBuilder()
        stringBuilder.append("Route: ")
        stringBuilder.append("\n")

        for((index,item) in route.withIndex())
        {
            if(index==0)
            {
                //print("[ $item, ")
                stringBuilder.append("[ $item, ")
            }
            else if(index>0 && index< route.size-1)
            {
                //print("$item, ")
                stringBuilder.append("$item, ")
            }
            else
            {
                //print("$item ]")
                stringBuilder.append("$item ]")
            }
        }
        //println()
        stringBuilder.append("\n")

        if(route.size in 1..9)
        {
            //println("Cost: ${ticketPrices[0]} EGP.")
            stringBuilder.append("Cost: ${ticketPrices[0]} EGP.")
            stringBuilder.append("\n")
        }
        else if(route.size in 10..16)
        {
            //println("Cost: ${ticketPrices[1]} EGP.")
            stringBuilder.append("Cost: ${ticketPrices[1]} EGP.")
            stringBuilder.append("\n")
        }
        else if(route.size > 10)
        {
            //println("Cost: ${ticketPrices[2]} EGP.")
            stringBuilder.append("Cost: ${ticketPrices[2]} EGP.")
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }

    fun getResult(): String
    {
        //val resultArray = findRoute(fromStationPos,toStationPos,froml,tol,fromIndex,toIndex,min,stations,from,to,ts,linePoints)

        return printRoute(findRoute(fromStationPos,toStationPos,froml,tol,fromIndex,toIndex,min,stations,from,to,ts,linePoints),ticketPrices)
    }

    fun caseOne(fromStationPos:Int,toStationPos:Int,fromIndex: Int,toIndex: Int,stations: Array<Station>):Array<String>
    {
        var route = arrayOf<String>()
        if(fromStationPos < toStationPos) {
            for (item in fromIndex..toIndex) {
                route += stations[item].name
            }
        }
        else
        {
            for(item in fromIndex downTo toStationPos)
            {
                route += stations[item].name
            }
        }
        return route
    }

    fun caseTwo(froml:Int, tol:Int, fromIndex:Int, toIndex:Int, ts:Array<Int>, stations: Array<Station>, firstLine:Int, secondLine:Int):Array<String>
    {
        var route = arrayOf<String>()
        if(froml < tol)
        {
            if(fromIndex - ts[firstLine] < 0)
            {
                for(item in fromIndex until ts[firstLine])
                {
                    route += stations[item].name
                }
                if(ts[secondLine] - toIndex < 0)
                {
                    for(item in ts[secondLine] .. toIndex)
                    {
                        route += stations[item].name
                    }
                }
                else
                {
                    for(item in ts[secondLine] downTo toIndex)
                    {
                        route += stations[item].name
                    }
                }
            }
            else
            {
                for(item in fromIndex downTo ts[firstLine]+1)
                {
                    route += stations[item].name
                }
                if(ts[secondLine] - toIndex < 0)
                {
                    for(item in ts[secondLine] .. toIndex)
                    {
                        route += stations[item].name
                    }
                }
                else
                {
                    for(item in ts[secondLine] downTo toIndex)
                    {
                        route += stations[item].name
                    }
                }
            }
        }
        else
        {
            if(fromIndex - ts[secondLine] < 0)
            {
                for(item in fromIndex until ts[secondLine])
                {
                    route += stations[item].name
                }
                if(ts[firstLine] - toIndex < 0)
                {
                    for(item in ts[firstLine] .. toIndex)
                    {
                        route += stations[item].name
                    }
                }
                else
                {
                    for(item in ts[firstLine] downTo toIndex)
                    {
                        route += stations[item].name
                    }
                }
            }
            else
            {
                for(item in fromIndex downTo ts[secondLine]+1)
                {
                    route += stations[item].name
                }
                if(ts[firstLine] - toIndex < 0)
                {
                    for(item in ts[firstLine] .. toIndex)
                    {
                        route += stations[item].name
                    }
                }
                else
                {
                    for(item in ts[firstLine] downTo toIndex)
                    {
                        route += stations[item].name
                    }
                }
            }
        }
        return route
    }

    private fun findRoute(fromStationPos: Int, toStationPos: Int, froml: Int, tol:Int, fromIndex: Int, toIndex: Int, min: Int, stations:Array<Station>, from:String, to:String, ts:Array<Int>, linePoints:Array<Int>):Array<String>
    {
        var route= arrayOf<String>()
        var fromStationPos = 0
        var toStationPos = 0
        var froml = 0
        var tol = 0
        var fromIndex = 0
        var toIndex = 0
        var isL1First = true
        var isL2First = true
        var isL3First = true
        var foundF = false
        var foundT = false
        var tsFIndex = 0
        var tsTIndex = 0
        var min = 9999999
        var minTs = 99999999
        var minTs1 = 99999999
        var stationPosInTs = 0
        var tsStationIndex = 0
        var midL = 0
        var isDone:Boolean = false
        var firstDigit = 0
        var secondDigit = 0
        var isFound = false
        var start = 0
        var end = 0
        var sStart = 0
        var sEnd = 0
        var currentLine = 0

        //======================================================================
        //The following part is responsible for checking in which line do the pickup and drop off location belong
        linePoints[0] = 0
        linePoints[5] = stations.size-1
        for((index, item) in stations.withIndex())
        {
            if(index > 0 && index < stations.size-1)
            {
                if(stations[index].line == "1" && stations[index+1].line == "2")
                {
                    linePoints[1] = index
                    linePoints[2] = index+1
                }
                else if(stations[index].line == "2" && stations[index+1].line == "3")
                {
                    linePoints[3] = index
                    linePoints[4] = index+1
                }
            }

            if(item.name == from)
            {
                fromIndex = index
                froml = try {
                    item.line.toInt()
                } catch (e: NumberFormatException) {
                    item.line.drop(1).toInt()
                }
                fromStationPos = index
            }
            else if(item.name == to)
            {
                toIndex = index
                tol = try {
                    item.line.toInt()
                } catch (e: NumberFormatException) {
                    item.line.drop(1).toInt()
                }
                toStationPos = index
            }

            if(item.line == "T12")
            {
                if(isL1First)
                {
                    ts[0] = index
                    isL1First=false
                }
                else
                {
                    ts[1] = index
                    isL1First=true
                }
            }
            else if(item.line == "T13")
            {
                if(isL2First)
                {
                    ts[2] = index
                    isL2First=false
                }
                else
                {
                    ts[3] = index
                    isL2First = true
                }
            }
            else if(item.line == "T23")
            {
                if(isL3First)
                {
                    ts[4] = index
                    isL3First = false
                }
                else
                {
                    ts[5] = index
                    isL3First = true
                }
            }
        }

        if(froml in 1..3 && tol > 11)
        {
            if(tol%10 == froml || tol/10 == froml)
            {
                firstDigit = tol%10
                secondDigit = tol/10
                tol = froml
                if(firstDigit == froml) {
                    if (firstDigit == 1) {
                        start = 0
                        end = 1
                    } else if (firstDigit == 2) {
                        start = 2
                        end = 3
                    } else if (firstDigit == 3) {
                        start = 4
                        end = 5
                    }
                }
                else {

                    if (secondDigit == 1) {
                        sStart = 0
                        sEnd = 1
                    } else if (secondDigit == 2) {
                        sStart = 2
                        sEnd = 3
                    } else if (secondDigit == 3) {
                        sStart = 4
                        sEnd = 5
                    }
                }

                for(index in linePoints[start] until linePoints[end])
                {
                    if(to == stations[index].name)
                    {
                        toIndex = index
                        toStationPos = index
                        isFound = true
                    }
                }
                if(!isFound)
                {
                    for(index in linePoints[sStart] until linePoints[sEnd])
                    {
                        if(to == stations[index].name)
                        {
                            toIndex = index
                            toStationPos = index
                            isFound = true
                        }
                    }
                }
            }
            else
            {
                for(item in ts)
                {
                    if(stations[item].name == to)
                    {
                        stationPosInTs = item
                        break
                    }
                }

                for (item in ts)
                {
                    if(abs(stationPosInTs - item) < minTs1 && stationPosInTs!=item)
                    {
                        minTs1 = abs(stationPosInTs - item)
                        tsStationIndex = item
                    }
                }
                for(item in 0 until linePoints.size-1 step 2)
                {
                    currentLine += 1
                    if(stationPosInTs >= linePoints[item] && stationPosInTs <= linePoints[item+1] && item%2 == 0)
                    {
                        tol = currentLine
                        toIndex = stationPosInTs
                        toStationPos = stationPosInTs
                    }
                }
            }
            //GET SECOND PART OF THE ROUTE
//        if (froml == 1 && tol == 2 || froml == 2 && tol == 1) {
//            if (froml == 1 && tol == 2) {
//                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 0, 1)
//            } else {
//                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 0, 1)
//            }
//        }else if (froml == 1 && tol == 3 || froml == 3 && tol == 1) {
//            if (froml == 1 && tol == 3) {
//                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 2, 3)
//            } else {
//                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 2, 3)
//            }
//        } else if (froml == 2 && tol == 3 || froml == 3 && tol == 2) {
//            if (froml == 2 && tol == 3) {
//                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 4, 5)
//            } else {
//                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 4, 5)
//            }
//        }
            //route+= caseTwo(froml,tol,fromIndex,toIndex,ts,stations,0,1)
            //isDone=true
        }
        else if(tol in 1..3 && froml > 11)
        {
            if(froml%10 == tol || froml/10 == tol)
            {
                firstDigit = froml%10
                secondDigit = froml/10
                froml = tol
                if(firstDigit == tol) {
                    if (firstDigit == 1) {
                        start = 0
                        end = 1
                    } else if (firstDigit == 2) {
                        start = 2
                        end = 3
                    } else if (firstDigit == 3) {
                        start = 4
                        end = 5
                    }
                }
                else {
                    if (secondDigit == 1) {
                        sStart = 0
                        sEnd = 1
                    } else if (secondDigit == 2) {
                        sStart = 2
                        sEnd = 3
                    } else if (secondDigit == 3) {
                        sStart = 4
                        sEnd = 5
                    }
                }

                for(index in linePoints[start] until linePoints[end])
                {
                    if(from == stations[index].name)
                    {
                        fromIndex = index
                        fromStationPos = index
                        isFound = true
                    }
                }
                if(!isFound)
                {
                    for(index in linePoints[sStart] until linePoints[sEnd])
                    {
                        if(from == stations[index].name)
                        {
                            fromIndex = index
                            fromStationPos = index
                            isFound = true
                        }
                    }
                }
            }
            else
            {
                for(item in ts)
                {
                    if(stations[item].name == from)
                    {
                        stationPosInTs = item
                        break
                    }
                }

                for (item in ts)
                {
                    if(abs(stationPosInTs - item)<minTs1 && stationPosInTs!=item)
                    {
                        minTs1 = abs(stationPosInTs - item)
                        tsStationIndex = item
                    }
                }
                for(item in linePoints.indices-1)
                {
                    if(stationPosInTs >= linePoints[item] && stationPosInTs <= linePoints[item+1] && item%2 == 0)
                    {
                        froml = abs(item-1)
                        fromIndex = stationPosInTs
                        fromStationPos = stationPosInTs
                    }
                }
            }
            //GET SECOND PART OF THE ROUTE
//        if (froml == 1 && tol == 2 || froml == 2 && tol == 1) {
//            if (froml == 1 && tol == 2) {
//                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 0, 1)
//            } else {
//                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 0, 1)
//            }
//        }else if (froml == 1 && tol == 3 || froml == 3 && tol == 1) {
//            if (froml == 1 && tol == 3) {
//                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 2, 3)
//            } else {
//                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 2, 3)
//            }
//        } else if (froml == 2 && tol == 3 || froml == 3 && tol == 2) {
//            if (froml == 2 && tol == 3) {
//                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 4, 5)
//            } else {
//                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 4, 5)
//            }
//        }
            //route+= caseTwo(froml,tol,fromIndex,toIndex,ts,stations,0,1)
            //isDone=true

        }
        else if(froml > 11 && tol > 11)
        {
            for((index,item) in ts.withIndex())
            {
                if(from == stations[item].name && !foundF)
                {
                    foundF = true
                    tsFIndex = index
                }
                if(to == stations[item].name && !foundT)
                {
                    foundT = true
                    tsTIndex = index
                }
                if(foundF && foundT)
                {
                    break
                }
            }

            if(tsFIndex%2 == 0 && tsTIndex%2 == 0)
            {
                //0,0
                if(abs(ts[tsFIndex] - ts[tsTIndex]) < minTs)
                {
                    //0,0
                    minTs = abs(ts[tsFIndex] - ts[tsTIndex])
                    fromIndex = ts[tsFIndex]
                    fromStationPos = ts[tsFIndex]
                    toIndex = ts[tsTIndex]
                    toStationPos = ts[tsTIndex]
                }
                if(abs(ts[tsFIndex+1] - ts[tsTIndex+1]) < minTs)
                {
                    //1,1
                    minTs = abs(ts[tsFIndex+1] - ts[tsTIndex+1])
                    fromIndex = ts[tsFIndex+1]
                    fromStationPos = ts[tsFIndex+1]
                    toIndex = ts[tsTIndex+1]
                    toStationPos = ts[tsTIndex+1]
                }
                if(abs(ts[tsFIndex] - ts[tsTIndex+1]) < minTs)
                {
                    //0,1
                    minTs = abs(ts[tsFIndex] - ts[tsTIndex+1])
                    fromIndex = ts[tsFIndex]
                    fromStationPos = ts[tsFIndex]
                    toIndex = ts[tsTIndex+1]
                    toStationPos = ts[tsTIndex+1]
                }
                if(abs(ts[tsFIndex+1] - ts[tsTIndex]) < minTs)
                {
                    //1,0
                    minTs = abs(ts[tsFIndex+1] - ts[tsTIndex])
                    fromIndex = ts[tsFIndex+1]
                    fromStationPos = ts[tsFIndex+1]
                    toIndex = ts[tsTIndex]
                    toStationPos = ts[tsTIndex]
                }
            }
            else if(tsFIndex%2 == 0 && tsTIndex%2 == 1)
            {
                //0,1
                if(abs(ts[tsFIndex] - ts[tsTIndex]) < minTs)
                {
                    //0,1
                    minTs = abs(ts[tsFIndex] - ts[tsTIndex])
                    fromIndex = ts[tsFIndex]
                    fromStationPos = ts[tsFIndex]
                    toIndex = ts[tsTIndex]
                    toStationPos = ts[tsTIndex]
                }
                if(abs(ts[tsFIndex+1] - ts[tsTIndex-1]) < minTs)
                {
                    //1,0
                    minTs = abs(ts[tsFIndex+1] - ts[tsTIndex-1])
                    fromIndex = ts[tsFIndex+1]
                    fromStationPos = ts[tsFIndex+1]
                    toIndex = ts[tsTIndex-1]
                    toStationPos = ts[tsTIndex-1]
                }
                if(abs(ts[tsFIndex] - ts[tsTIndex-1]) < minTs)
                {
                    //0,0
                    minTs = abs(ts[tsFIndex] - ts[tsTIndex-1])
                    fromIndex = ts[tsFIndex]
                    fromStationPos = ts[tsFIndex]
                    toIndex = ts[tsTIndex-1]
                    toStationPos = ts[tsTIndex-1]
                }
                if(abs(ts[tsFIndex+1] - ts[tsTIndex]) < minTs)
                {
                    //1,1
                    minTs = abs(ts[tsFIndex+1] - ts[tsTIndex])
                    fromIndex = ts[tsFIndex+1]
                    fromStationPos = ts[tsFIndex+1]
                    toIndex = ts[tsTIndex]
                    toStationPos = ts[tsTIndex]
                }
            }
            else if(tsFIndex%2 == 1 && tsTIndex%2 == 0)
            {
                //1,0
                if(abs(ts[tsFIndex] - ts[tsTIndex]) < minTs)
                {
                    //1,0
                    minTs = abs(ts[tsFIndex] - ts[tsTIndex])
                    fromIndex = ts[tsFIndex]
                    fromStationPos = ts[tsFIndex]
                    toIndex = ts[tsTIndex]
                    toStationPos = ts[tsTIndex]
                }
                if(abs(ts[tsFIndex-1] - ts[tsTIndex+1]) < minTs)
                {
                    //0,1
                    minTs = abs(ts[tsFIndex-1] - ts[tsTIndex+1])
                    fromIndex = ts[tsFIndex-1]
                    fromStationPos = ts[tsFIndex-1]
                    toIndex = ts[tsTIndex+1]
                    toStationPos = ts[tsTIndex+1]
                }
                if(abs(ts[tsFIndex-1] - ts[tsTIndex]) < minTs)
                {
                    //0,0
                    minTs = abs(ts[tsFIndex-1] - ts[tsTIndex])
                    fromIndex = ts[tsFIndex-1]
                    fromStationPos = ts[tsFIndex-1]
                    toIndex = ts[tsTIndex]
                    toStationPos = ts[tsTIndex]
                }
                if(abs(ts[tsFIndex] - ts[tsTIndex+1]) < minTs)
                {
                    //1,1
                    minTs = abs(ts[tsFIndex] - ts[tsTIndex+1])
                    fromIndex = ts[tsFIndex]
                    fromStationPos = ts[tsFIndex]
                    toIndex = ts[tsTIndex+1]
                    toStationPos = ts[tsTIndex+1]
                }
            }
            else
            {
                //1,1
                if(abs(ts[tsFIndex] - ts[tsTIndex]) < minTs)
                {
                    //1,1
                    minTs = abs(ts[tsFIndex] - ts[tsTIndex])
                    fromIndex = ts[tsFIndex]
                    fromStationPos = ts[tsFIndex]
                    toIndex = ts[tsTIndex]
                    toStationPos = ts[tsTIndex]
                }
                if(abs(ts[tsFIndex-1] - ts[tsTIndex-1]) < minTs)
                {
                    //0,0
                    minTs = abs(ts[tsFIndex-1] - ts[tsTIndex-1])
                    fromIndex = ts[tsFIndex-1]
                    fromStationPos = ts[tsFIndex-1]
                    toIndex = ts[tsTIndex-1]
                    toStationPos = ts[tsTIndex-1]
                }
                if(abs(ts[tsFIndex] - ts[tsTIndex-1]) < minTs)
                {
                    //1,0
                    minTs = abs(ts[tsFIndex] - ts[tsTIndex-1])
                    fromIndex = ts[tsFIndex]
                    fromStationPos = ts[tsFIndex]
                    toIndex = ts[tsTIndex-1]
                    toStationPos = ts[tsTIndex-1]
                }
                if(abs(ts[tsFIndex-1] - ts[tsTIndex]) < minTs)
                {
                    //0,1
                    minTs = abs(ts[tsFIndex-1] - ts[tsTIndex])
                    fromIndex = ts[tsFIndex-1]
                    fromStationPos = ts[tsFIndex-1]
                    toIndex = ts[tsTIndex]
                    toStationPos = ts[tsTIndex]
                }
            }

            for(item in linePoints.indices-1)
            {
                if(fromIndex >= linePoints[item] && fromIndex <= linePoints[item+1] && item%2 == 0)
                {
                    froml = abs(item-1)
                }
                if(toIndex >= linePoints[item] && toIndex <= linePoints[item+1] && item%2 == 0)
                {
                    tol = abs(item-1)
                }
            }
        }


        //===================================================
        //The following part is responsible for finding the best route from the pickup location to the drop-off location
        println(stations[18].line)
        println("from: $froml at position number: $fromStationPos and to: $tol at position number: $toStationPos")
        printTs(ts)
        //if(isDone==false) {
        if (froml == 1 && tol == 1) {
            route = caseOne(fromStationPos, toStationPos, fromIndex, toIndex, stations)
        } else if (froml == 2 && tol == 2) {
            route = caseOne(fromStationPos, toStationPos, fromIndex, toIndex, stations)
        } else if (froml == 3 && tol == 3) {
            route = caseOne(fromStationPos, toStationPos, fromIndex, toIndex, stations)
        } else if (froml == 1 && tol == 2 || froml == 2 && tol == 1) {
            if (froml == 1 && tol == 2) {
                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 0, 1)
            } else {
                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 0, 1)
            }
        } else if (froml == 1 && tol == 3 || froml == 3 && tol == 1) {
            if (froml == 1 && tol == 3) {
                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 2, 3)
            } else {
                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 2, 3)
            }
        } else if (froml == 2 && tol == 3 || froml == 3 && tol == 2) {
            if (froml == 2 && tol == 3) {
                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 4, 5)
            } else {
                route = caseTwo(froml, tol, fromIndex, toIndex, ts, stations, 4, 5)
            }
        }
        // }
        //===================================================
        return route
    }
}