package DatabaseTest;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserAccountRepo extends MongoRepository<Account, String> {

}
