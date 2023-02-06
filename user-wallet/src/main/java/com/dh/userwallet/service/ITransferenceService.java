package com.dh.userwallet.service;

import com.dh.userwallet.model.Transference;

public interface ITransferenceService {
   Transference generateTransference(Transference transference, Long idAccount);


}
