package com.example.library.model;

import java.time.LocalDate;

public class Transaction {
    private String transactionId;
    private Member member;
    private Book book;
    private boolean isReturned;
    private boolean status;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;

    public Transaction(String transactionId, Member member, Book book, LocalDate borrowedDate) {
        this.transactionId = transactionId;
        this.member = member;
        this.book = book;
        this.isReturned = false;
        this.borrowedDate = borrowedDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public boolean processBorrow() {
        if (!member.canBorrow()) {
            System.out.println("GAGAL [" + transactionId + "]: " + member.getName()
                    + " sudah mencapai batas maksimal peminjaman ("
                    + member.getBorrowedCount() + "/" + member.getMaxLimit() + ").");
            return false;
        }

        boolean stockReduced = book.decreaseStock();
        if (!stockReduced) {
            System.out.println("GAGAL [" + transactionId + "]: Stok buku \""
                    + book.getTitle() + "\" habis.");
            return false;
        }

        member.incrementBorrowed();
        isReturned = false;
        System.out.println("SUKSES [" + transactionId + "]: " + member.getName()
                + " berhasil meminjam \"" + book.getTitle() + "\". Sisa stok: "
                + book.getStock() + ", Total dipinjam anggota: "
                + member.getBorrowedCount() + "/" + member.getMaxLimit());
        return true;
    }

    public boolean processReturn() {
        if (isReturned) {
            System.out.println("GAGAL [" + transactionId + "]: Buku \""
                    + book.getTitle() + "\" sudah pernah dikembalikan sebelumnya.");
            return false;
        }

        book.increaseStock();
        member.decrementBorrowed();
        isReturned = true;

        System.out.println("SUKSES [" + transactionId + "]: " + member.getName()
                + " berhasil mengembalikan \"" + book.getTitle() + "\". Stok sekarang: "
                + book.getStock() + ", Total dipinjam anggota: "
                + member.getBorrowedCount() + "/" + member.getMaxLimit());
        return true;
    }

    @Override
    public String toString() {
        return "Transaksi " + transactionId + " | Anggota: " + member.getName()
                + " | Buku: " + book.getTitle()
                + " | Status: " + (isReturned ? "Dikembalikan" : "Sedang Dipinjam");
    }
}