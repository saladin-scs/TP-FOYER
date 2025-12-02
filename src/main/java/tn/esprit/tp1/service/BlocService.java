package tn.esprit.tp1.service;

import tn.esprit.tp1.entity.Bloc;

import java.util.List;

public interface BlocService {

    Bloc addBloc(Bloc bloc);

    Bloc updateBloc(Long id, Bloc bloc);

    void deleteBloc(Long id);

    List<Bloc> getAllBlocs();

    Bloc getBlocById(Long id);

    Bloc affecterChambresABloc(List<Long> idsChambres, Long idBloc);



}
