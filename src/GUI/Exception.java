package GUI;

public  class Exception {
    class InvalidCommandException extends Exception {
        public InvalidCommandException(String message) {

        }
    }

    class InformationIncompleteException extends Exception {
        public InformationIncompleteException(String message) {

        }
    }
}