# Junit Bank App

### Jpa LocalDateTime 자동으로 생성하는 법
- @EnableJpaAuditing (Main 클래스)
- @EntityListeners(AuditingEntityListener.class) (Entity 클래스)
```java
    @CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;
```

### Entity Class 고정 Anotation
```java
    @NoArgsConstructor // 스프링이 User 객체 생성할 때 빈생성자로 new 하기 때문에!!
    @Getter
    @EntityListeners(AuditingEntityListener.class)
    @Table(name = "user_tb")
    @Entity
```
# bank
