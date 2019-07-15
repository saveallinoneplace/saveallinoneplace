package main

import (
	"fmt"
	"log"

	"github.com/google/gopacket"
	"github.com/google/gopacket/layers"
	"github.com/google/gopacket/pcap"
)

var (
	pcapFile string = "capture-Bridge0-May 24 12-50-28.pcapng"
	handle   *pcap.Handle
	err      error
)

func main() {

	handle, err = pcap.OpenOffline(pcapFile)
	if err != nil {
		log.Fatal(err)
	}

	defer handle.Close()

	var packetCount int = 0
	var sizeOfUDP int = 0
	var packetCountUDP int = 0

	packetAll := gopacket.NewPacketSource(handle, handle.LinkType())

	for packet := range packetAll.Packets() {
		if isUDP(packet) {
			sizeOfUDP += packet.Metadata().Length
			packetCountUDP++
		}
		packetCount++
	}

	fmt.Println("Amount of all packets:", packetCount)
	fmt.Println("Amount of UDP:", packetCountUDP)
	fmt.Println("Avarage size of UDP:", (sizeOfUDP / packetCountUDP))
}

func isUDP(packet gopacket.Packet) bool {
	UDPLayer := packet.Layer(layers.LayerTypeUDP)
	if UDPLayer != nil {
		return true
	} else {
		return false
	}
}
