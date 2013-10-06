import twitter4j._

class ReplyListener extends UserStreamListener {
  override def onStatus(status: Status) {
    println("New interesting status: ", status.toString)
  }

  def onException(ex: Exception) {}

  def onStallWarning(warning: StallWarning) {}

  def onScrubGeo(userId: Long, upToStatusId: Long) {}

  def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}

  def onDeletionNotice(directMessageId: Long, userId: Long) {}

  def onFriendList(friendIds: Array[Long]) {}

  def onFavorite(source: User, target: User, favoritedStatus: Status) {}

  def onUnfavorite(source: User, target: User, unfavoritedStatus: Status) {}

  def onFollow(source: User, followedUser: User) {}

  def onDirectMessage(directMessage: DirectMessage) {}

  def onUserListMemberAddition(addedMember: User, listOwner: User, list: UserList) {}

  def onUserListMemberDeletion(deletedMember: User, listOwner: User, list: UserList) {}

  def onUserListSubscription(subscriber: User, listOwner: User, list: UserList) {}

  def onUserListUnsubscription(subscriber: User, listOwner: User, list: UserList) {}

  def onUserListCreation(listOwner: User, list: UserList) {}

  def onUserListUpdate(listOwner: User, list: UserList) {}

  def onUserListDeletion(listOwner: User, list: UserList) {}

  def onUserProfileUpdate(updatedUser: User) {}

  def onBlock(source: User, blockedUser: User) {}

  def onUnblock(source: User, unblockedUser: User) {}

  def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
}
