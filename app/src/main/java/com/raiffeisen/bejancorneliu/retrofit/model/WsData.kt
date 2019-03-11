package com.raiffeisen.bejancorneliu.retrofit.model

class WsData(val results : ArrayList<WsDataResults>)


class WsDataResults(val name : WsDataName,val dob : WsDataDob, val nat : String,val picture : WsDataPicture)

class WsDataName(val first : String, val last : String)
class WsDataDob(val age : String)
class WsDataPicture(val large : String,val thumbnail : String)
