import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AdIdPlugin)
public class AdIdPlugin: CAPPlugin {
    // private let implementation = AdId()

    @objc func echo(_ call: CAPPluginCall) {
        // let value = call.getString("value") ?? ""
        // call.resolve([
        //     "value": implementation.echo(value)
        // ])
        self.requestIDFAPermission(_ call)
    }

        
      var idfa: UUID {
        return ASIdentifierManager.shared().advertisingIdentifier
      }
    
    
    func requestIDFAPermission(_ call: CAPPluginCall) {

        if #available(iOS 14, *) {
            ATTrackingManager.requestTrackingAuthorization { (status) in
                switch status {

                case .authorized:
                    print("Authorized")
                    print("IDFA: ", self.idfa)
                    // completion(self.idfa.uuidString)
                    call.resolve([
                        "value": self.idfa.uuidString
                    ])

                case .denied, .notDetermined, .restricted:
                    print("Not Authorized")
                    print("IDFA: ", self.idfa)
               
                    call.resolve([
                        "value": self.idfa.uuidString
                    ])
                @unknown default:
                    print("UNKNOWN")
                    print("IDFA: ", self.idfa)
                    call.resolve([
                        "value": self.idfa.uuidString
                    ])
                }
            }
        } else {
            print("Under 14.0")
            print("IDFA: ", self.idfa)

            call.resolve([
                    "value": self.idfa.uuidString
            ])                    
        }
    }

}
