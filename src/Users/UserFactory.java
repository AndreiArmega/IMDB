package Users;

public class UserFactory {
    public static User createUser(AccountType accountType) {
        switch (accountType) {
            case REGULAR:
                return new Regular(accountType);
            case CONTRIBUTOR:
                return new Contributor(accountType);
            case ADMIN:
                return new Admin(accountType);
            default:
                throw new IllegalArgumentException("Invalid account type: " + accountType);
        }
    }
}
