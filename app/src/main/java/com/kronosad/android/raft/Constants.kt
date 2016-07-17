package com.kronosad.android.raft

import com.myjeeva.digitalocean.impl.DigitalOceanClient

/**
 * @author Russell Richardson
 */
object Constants {
    val AUTHENTICATION_FINISHED_WITH_TOKEN = "com.kronosad.android.raft.AUTHENTICATION_FINISHED_WITH_TOKEN"
    val OAUTH_URL = "https://cloud.digitalocean.com/v1/oauth/authorize?client_id=ecb4694ef92bcb1991d71a5ebd68af050ffe83bbfedebe811cdb1d425e914a91&redirect_uri=raft://authorize/&response_type=code"

    // Probably shouldn't be in Constants, but oh well :P
    lateinit var client: DigitalOceanClient

}
