pragma solidity ^0.5.0;

import "./Ownable.sol";

contract Registry {

    modifier onlyMembers() {
        require(members[msg.sender]);
        _;
    }

    modifier onlyCertifier(address _address) {
        require(certifications[_address].certifier == msg.sender);
        _;
    }

    mapping(address => bool) members;

    struct Certification {
        address certifier;
        bytes32 proof;
        uint256 expirationDate;
    }

    mapping(address => Certification) public certifications;

    function addCertification(address _address, bytes32 _proof, uint256 _expirationDate) onlyMembers public {
        certifications[_address] = Certification({
            certifier : msg.sender,
            proof : _proof,
            expirationDate : _expirationDate
            });
    }

    function revokeCertification(address _address) public onlyCertifier(_address) {
        delete certifications[_address];
    }

    function extendExpiration(address _address, uint256 newExpirationDate) onlyCertifier(_address) public {
        certifications[_address].expirationDate = newExpirationDate;
    }

}