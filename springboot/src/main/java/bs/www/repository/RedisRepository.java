package bs.www.repository;

public interface RedisRepository {

    void setJWT(String jwt);

    boolean isJWTExist(String jwt);

    void upDateJWTExpire(String jwt);

    Long getExpire(String jwt);
}
