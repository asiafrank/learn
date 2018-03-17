package com.asiafrank.bangumi.core.model

/**
  * Torrent - table `torrent` inherits `resource`
  *
  * Created at 07/2/2017.
  *
  * @author asiafrank
  */
@SerialVersionUID(-1L)
class Torrent extends Resource with Serializable {
  // https://github.com/mjw56/torrent-client/blob/master/src/GivenTools/TorrentInfo.java
  // https://en.wikipedia.org/wiki/Torrent_file
  // http://www.codecommit.com/blog/java/bencode-stream-parsing-in-java
  // TODO: complete the fields of torrent
}