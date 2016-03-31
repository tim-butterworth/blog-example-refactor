# Example Blog

The core domain of the Blog lives in the `blog` package. It is an attempt to accomplish the Clean Architecture / Ports and Adapters / Hexagonal Architecture pattern in Java. The goal is to have the `blog` domain unaware of *how* the blog posts are stored.  Storage should be a plug-in implementing the PostRepository interface.


