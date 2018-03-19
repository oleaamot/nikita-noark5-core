package nikita.webapp.util.error;

public class ApiError {

    private int status;
    private String message;
    private String developerMessage;
    private String stackTrace;

    public ApiError(final int status, final String message, final String developerMessage, final String stackTrace) {
        super();

        this.status = status;
        this.message = message;
        this.developerMessage = developerMessage;
        this.stackTrace = stackTrace;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(final String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    //

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((developerMessage == null) ? 0 : developerMessage.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + status;
        return result;
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other)
            return true;
        if (!(other instanceof ApiError))
            return false;
        final ApiError apiError = (ApiError) other;
        if (developerMessage == null) {
            if (apiError.developerMessage != null) {
                return false;
            }
        } else if (!developerMessage.equals(apiError.developerMessage)) {
            return false;
        }
        if (message == null) {
            if (apiError.message != null) {
                return false;
            }
        } else if (!message.equals(apiError.message)) {
            return false;
        }
        return status == apiError.status;
    }

    // ignore stackTrace here!
    @Override
    public final String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ApiError [status=").append(status).append(", message=").append(message).append(", developerMessage=").append(developerMessage).append("]");
        return builder.toString();
    }

    /**
     * @return The details as a JSON string
     */
    public final String toJSON() {
        final StringBuilder json = new StringBuilder();
        json.append("{  \"status\" : ").append(status).append(", \"message\" : ").append(message).append(", \"developerMessage\" : ").append(developerMessage).append("}");
        return json.toString();
    }
}
