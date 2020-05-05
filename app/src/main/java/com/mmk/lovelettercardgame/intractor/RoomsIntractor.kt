package com.mmk.lovelettercardgame.intractor

import android.util.EventLog
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.mmk.lovelettercardgame.api.ApiInitHelper
import com.mmk.lovelettercardgame.api.SocketInstance
import com.mmk.lovelettercardgame.pojo.ResponseAddRoomPojo
import com.mmk.lovelettercardgame.pojo.ResponseRoomListPOJO
import org.json.JSONObject
import retrofit2.Callback


class RoomsIntractor {
    private val EVENT_GET_ROOMS="get-rooms"
    private val EVENT_RECEIVE_ROOMS="receive-rooms"
    private val EVENT_NEW_ROOM="new-room"
    private val EVENT_CREATED_ROOM="created-room"
    private val EVENT_ENTER_ROOM="enter-room"
    private val EVENT_ENTER_ROOM_RESPONSE="response"
    private val EVENT_PLAYERS_RESPONSE="update-room"







    private val mSocket=SocketInstance.getInstance()
    init {
        mSocket?.connect()
    }

    fun getRoomsList(listener: Emitter.Listener){

        mSocket?.emit(EVENT_GET_ROOMS)
        mSocket?.on(EVENT_RECEIVE_ROOMS,listener)

    }

    fun addRoom(name:String,maxNBPlayers:String,listener: Emitter.Listener){
        val roomJSONObject=JSONObject()
        roomJSONObject.put("roomName",name)
        roomJSONObject.put("maxPlayers",maxNBPlayers)

        mSocket?.emit(EVENT_NEW_ROOM,roomJSONObject)
        mSocket?.on(EVENT_CREATED_ROOM,listener)

    }

    fun joinRoom(playerName:String,roomId:String,listener: Emitter.Listener){
        val roomEnterJSONObject=JSONObject()
        roomEnterJSONObject.put("roomId",roomId)
        roomEnterJSONObject.put("nickName",playerName)

        println("Join $roomId and $playerName")
        mSocket?.emit(EVENT_ENTER_ROOM,roomEnterJSONObject)
        mSocket?.on(EVENT_ENTER_ROOM_RESPONSE,listener)
    }

    fun getPlayers(listener: Emitter.Listener){
        mSocket?.on(EVENT_PLAYERS_RESPONSE,listener)
    }

    fun closeServer(){
        mSocket?.disconnect()
        mSocket?.off("get-rooms")
        mSocket?.off("receive-rooms")
    }







}


