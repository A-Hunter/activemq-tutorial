package model;

public class Event {

        private String type;
        private Integer clientId;

        public Event(String type, Integer clientId) {
            this.type = type;
            this.clientId = clientId;
        }

        public String getType() {
            return type;
        }

        public Integer getClientId() {
            return clientId;
        }

        public String toString() {
            return "Event: type(" + type + "), clientId(" + clientId + ")";
        }

        public String getClientDetailUri() {
            return "https://localhost:8080/support/customer/" + clientId;
        }

}
