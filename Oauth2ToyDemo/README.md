# Notes

## Steps
- Go to the page http://localhost:8080/app-index-servlet.
- Redirect (get call) to http://localhost:8080/oauth-servlet?reqType=oauth with response_type, redirect_uri, app_id, and scope.
- Redirect to http://localhost:8080/approve.jsp to check the authorized scope.
- Select scope, then make a post call (form request) to http://localhost:8080/oauth-servlet to get the code.
- Redirect to http://localhost:8080/app-servlet.
- Make a post call to oauth service http://localhost:8080/oauth-servlet to get the access token.
- After get the access token, make call to protected service http://localhost:8080/protected-servlet

## Oauth 2 Basic Knolwedge
Methods to get access tokens from the authorization server are called __grants__. The same method used to request a token is also used by the resource server to validate a token.

The four basic grant types are __Authorization Code__, __Implicit__, __Resource Owner Credentials__ and __Client Credentials__. 

There are several roles involved in the OAuth 2.0 protocol: RO (Resource Owner), RS (Resource Server), AS (Authorization Server), and Client.  
- Front channel: RO, Client, AS
- Back channel: Client, AS, RS
