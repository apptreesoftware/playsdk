package sdk.emailNotifications;

import java.util.Collection;
import java.util.List;

/**
 * Created by alexis on 12/5/16.
 */
public class EmailRequest {
    public Collection<String> roles;
    public Collection<String> statuses;
    public Collection<String> userIDs;
    public String operator;
    public String subject;
    public String body;
    public String replyTo;
    public Collection<String> to;
    public Collection<String> cc;

    private EmailRequest(Collection<String> roles, Collection<String> statuses, Collection<String> userIDs, String operator, String subject, String body, String replyTo, Collection<String> to, Collection<String> cc) {
        this.roles = roles;
        this.statuses = statuses;
        this.userIDs = userIDs;
        this.operator = operator;
        this.subject = subject;
        this.body = body;
        this.replyTo = replyTo;
        this.to = to;
        this.cc = cc;
    }

    public static class Builder {
        private Collection<String> roles;
        private Collection<String> statuses;
        private Collection<String> userIDs;
        private String operator;
        private String subject;
        private String body;
        private String replyTo;
        private Collection<String> to;
        private Collection<String> cc;

        public Builder(String replyTo) {
            this.replyTo = replyTo;
        }

        public Builder withRolesAndStatuses(Collection<String> roles, Collection<String> statuses, String operator) {
            userIDs = null;
            this.roles = roles;
            this.statuses = statuses;
            this.operator = operator;
            return this;
        }

        public Builder withUserIDs(Collection<String> userIDs) {
            roles = null;
            statuses = null;
            this.userIDs = userIDs;
            return this;
        }

        public Builder withOtherRecipients(List<String> to) {
            this.to = to;
            return this;
        }

        public Builder withCC(List<String> cc) {
            this.cc = cc;
            return this;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Builder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailRequest build() {
            EmailRequest emailRequest = new EmailRequest(roles, statuses, userIDs, operator, subject, body, replyTo, to, cc);
            if ((emailRequest.userIDs == null || emailRequest.userIDs.size() == 0) && (emailRequest.statuses == null || emailRequest.statuses.size() == 0) && (emailRequest.roles == null || emailRequest.roles.size() == 0) && (emailRequest.to == null || emailRequest.to.size() ==0)) {
                throw new RuntimeException("Invalid list of users to send email to");
            }
            if ( emailRequest.subject == null || emailRequest.body == null ) {
                throw new RuntimeException("Empty subject or body");
            }
            return emailRequest;
        }
    }
}
