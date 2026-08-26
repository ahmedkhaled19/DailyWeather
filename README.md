**🏗️ Architecture**

The project follows Clean Architecture with three main layers: Presentation → Domain → Data

**Presentation Layer** : Responsible for everything related to the UI and user interaction.

**Domain Layer** : Contains the application’s business logic and should remain independent of Android and external frameworks.

**Data Layer** : Responsible for retrieving and transforming data from external sources.

------------------

**🧰 Tech Stack**

**Kotlin** : The application is written entirely in Kotlin.

**Coroutines** :  Kotlin Coroutines are used for asynchronous operations.

**Flow** : Kotlin Flow is used for reactive data streams and state propagation.

**Hilt** : is used for Dependency Injection.

**Retrofit** : is used for communicating with the API.

------------------

**🔄 Data Flow**

The application follows a clear one-directional flow. 

------------------

**🔀 Repository Pattern**

The domain layer defines the repository abstraction and the data layer provides its implementation.

------------------

**🔄 Mapper**

API DTOs are not exposed directly to the domain layer. Instead, a mapper converts to domain model.

------------------

**🎯 Use Cases**

Application actions are represented using use cases. The use case represents the business operation of retrieving weather information.
This keeps business logic outside the ViewModel and makes individual operations easier to test.

------------------

**🧪 Testing**

The architecture is designed to make each layer independently testable.

The main testing targets are: Repository, Use Case, ViewModel.

------------------

**🚀 Main Principles**

The project follows these principles:

	* Clean Architecture
	* MVVM
	* SOLID
	* Separation of Concerns
	* Dependency Inversion
	* Repository Pattern
	* Dependency Injection
	* Single Responsibility
	* Testability
	* Unidirectional data flow
