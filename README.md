# tkm-mock-circuit

To generate a working RSA key pair for Visa execute the following commands:

openssl genrsa -out private-key.pem 3072
req -new -x509 -key private-key.pem -out cert.pem -days 3600