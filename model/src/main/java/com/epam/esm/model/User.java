package com.epam.esm.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * User entity.
 */
@Entity
@Table(name = "shop_user")
public class User implements Model{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "shopUser", fetch = FetchType.LAZY)
    private Set<Order> orders;

    public User() {
    }

    public User(Integer id, String nickname, LocalDateTime createDate) {
        this.id = id;
        this.nickname = nickname;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreateDate() {
        this.createDate = LocalDateTime.now();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLastUpdateDate() {
        // Because there is no possibility to update tag but the method needs to be implemented - it stays empty.
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(nickname, user.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + nickname + '\'' +
                ", createDate=" + createDate +
                ", orders=" + orders +
                '}';
    }
}
