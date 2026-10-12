package com.example.library.model;

public class Member {
    private String memberId;
    private String name;
    private int borrowedCount;
    private static final int MAX_LIMIT = 3;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.borrowedCount = 0;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public int getBorrowedCount() {
        return borrowedCount;
    }

    public static int getMaxLimit() {
        return MAX_LIMIT;
    }

    public boolean canBorrow() {
        return getBorrowedCount() < MAX_LIMIT;
    }

    public boolean incrementBorrowed() {
        if (this.canBorrow()) {
            this.borrowedCount++;
            return true;
        }

        return false;
    }

    public void decrementBorrowed() {
        if (getBorrowedCount() > 0) {
            this.borrowedCount--;
        }
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", name='" + name + '\'' +
                ", borrowedCount=" + borrowedCount +
                '}';
    }
}