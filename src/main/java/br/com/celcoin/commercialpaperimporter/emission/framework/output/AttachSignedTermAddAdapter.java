package br.com.celcoin.commercialpaperimporter.emission.framework.output;

import br.com.celcoin.commercialpaperimporter.emission.application.ports.output.AttachSignedTermAddOutput;
import br.com.celcoin.commercialpaperimporter.emission.domain.entity.AttachSignedTerm;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.AttachSignedTermApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.api.TokenApi;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.rest.mappers.AttachSignedTermMapper;
import br.com.celcoin.commercialpaperimporter.emission.framework.output.s3.GetFile;

public class AttachSignedTermAddAdapter implements AttachSignedTermAddOutput {

    private AttachSignedTermMapper mapper;

    public AttachSignedTermAddAdapter() {
        this.mapper = new AttachSignedTermMapper();
    }

    @Override
    public AttachSignedTerm add(String id, String bucketName, String keyName) {
        byte[] constitutiveTerm = new GetFile().getFile(bucketName, keyName);

        var token = new TokenApi().getAccessToken();
        return mapper.fromDto(new AttachSignedTermApi().postAttachSignedTerm(token, id, constitutiveTerm));
    }

}
