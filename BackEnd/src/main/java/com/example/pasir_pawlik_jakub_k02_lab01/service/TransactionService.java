    package com.example.pasir_pawlik_jakub_k02_lab01.service;

    import com.example.pasir_pawlik_jakub_k02_lab01.dto.BalanceDTO;
    import com.example.pasir_pawlik_jakub_k02_lab01.dto.TransactionDTO;
    import com.example.pasir_pawlik_jakub_k02_lab01.model.Transaction;
    import com.example.pasir_pawlik_jakub_k02_lab01.model.TransactionType;
    import com.example.pasir_pawlik_jakub_k02_lab01.model.User;
    import com.example.pasir_pawlik_jakub_k02_lab01.repository.TransactionRepository;
    import com.example.pasir_pawlik_jakub_k02_lab01.repository.UserRepository;
    import jakarta.persistence.EntityNotFoundException;
    import jakarta.validation.Valid;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Service;


    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.List;

    @Service
    public class TransactionService {
        private final TransactionRepository transactionRepository;
        private final UserRepository userRepository;

        public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
            this.transactionRepository = transactionRepository;
            this.userRepository = userRepository;
        }

        public List<Transaction> getAllTransactions() {
            User user = getCurrentUser();
            return transactionRepository.findAllByUser(user);
        }

        public Transaction getTransactionById(Long id) {
            return transactionRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono transakcji o ID " + id));
        }

        public Transaction createTransaction(TransactionDTO transactionDTO) {
            Transaction transaction = new Transaction();
            transaction.setAmount(transactionDTO.getAmount());
            transaction.setType(TransactionType.valueOf(transactionDTO.getType()));
            transaction.setTags(transactionDTO.getTags());
            transaction.setNotes(transactionDTO.getNotes());
            transaction.setUser(getCurrentUser());
            transaction.setTimestamp(LocalDate.now());
            return transactionRepository.save(transaction);
        }

        public Transaction updateTransaction(Long id, TransactionDTO transactionDTO) {
            Transaction transaction = transactionRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono transakcji o ID " + id));

            if (!transaction.getUser().getEmail().equals(getCurrentUser().getEmail())) {
                throw new SecurityException("Brak dostępu do edycji tej transakcji");
            }

            transaction.setAmount(transactionDTO.getAmount());
            transaction.setType(TransactionType.valueOf(transactionDTO.getType()));
            transaction.setTags(transactionDTO.getTags());
            transaction.setNotes(transactionDTO.getNotes());

            return transactionRepository.save(transaction);
        }

        public void deleteTransaction(Long id) {
            if (!transactionRepository.existsById(id)) {
                throw new EntityNotFoundException("Nie znaleziono transakcji o ID " + id);
            }
            transactionRepository.deleteById(id);
        }

        public User getCurrentUser(){
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Nie znaleziono zalogowanego użytkownika"));
        }

        public BalanceDTO getUserBalance(User user) {
            List<Transaction> userTransactions = transactionRepository.findAllByUser((user));

            double income = userTransactions.stream().filter(t -> t.getType() == TransactionType.INCOME).mapToDouble(Transaction::getAmount).sum();

            double expense = userTransactions.stream().filter(t -> t.getType() == TransactionType.EXPENSE).mapToDouble(Transaction::getAmount).sum();

            return new BalanceDTO(income, expense, income - expense);
        }

    }
