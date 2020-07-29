package com.google.sps.utils;
import com.google.appengine.api.datastore.Entity;
import java.lang.IllegalArgumentException;
import java.util.List;
  
/**
 * Wrapper around a datastore Entity representing a user.
 *
 * This adds some type safety and relieves the need for type casting when
 * handling user entities.
 */
public final class User {

  // The "kind" of entity that represents a user.
  // Use `new Entity(User.USER_ENTITY_KIND)` to construct user entities.
  public final static String USER_ENTITY_KIND = "user";

  private Entity entity;

  public User(Entity userEntity) {
    if (userEntity.getKind() != USER_ENTITY_KIND) {
      throw new IllegalArgumentException(
          "Attempted to interpret Entity of kind " + userEntity.getKind()
          + " as a User.");
    }
    entity = userEntity;
  }

  // Retrieves (by reference) the entity managed by this instance.
  public Entity getEntity() {
    return entity;
  }

  // The game, identified by ID, in which this user is currently participating.
  public String getGame() {
    return (String) entity.getProperty("gameId");
  }
  public void setGame(String gameId) {
    entity.setProperty("gameId", gameId);
  }

  // Unique identifier for the user.
  public String getId() {
    return (String) entity.getProperty("userID");
  }
  // TODO: Consider raising an exception instead of overwriting a non-null userId.
  public void setId(String userID) {
    entity.setProperty("userID", userID);
  }

  // Non-unique name for the user
  public String getName(){
    return (String) entity.getProperty("userName");
  }
  public void setName(String userName){
    entity.setProperty("userName", userName);
  }

  // current game score
  public int getScore(){
    return ((Number) entity.getProperty("score")).intValue();
  }
  public void setScore(int score){
    entity.setProperty("score", score);
  }

  // time the last quiz was taken
  public long getQuizTiming(){
    return (long) entity.getProperty("quiz_timing");
  }
  public void setQuizTiming(long quiz_timing){
    entity.setProperty("quiz_timing", quiz_timing);
  }

  // image blobKey -- used to create an image url
  // the user's current plant picture
  public String getBlobKey(){
    return (String) entity.getProperty("blobKey");
  }
  public void setBlobKey(String blobKey){
    entity.setProperty("blobKey", blobKey);
  } 
}