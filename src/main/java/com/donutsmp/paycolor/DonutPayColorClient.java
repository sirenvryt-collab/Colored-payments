package com.donutsmp.paycolor;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Recolors Donut SMP payment messages in chat:
 *   - "You paid <name> $<amount>"  -> amount shown in RED
 *   - "<name> paid you $<amount>"  -> amount shown in GREEN
 *
 * If you need to tweak the wording it matches, edit the two Pattern
 * definitions below. They're intentionally loose so small formatting
 * differences (extra spaces, commas, decimals, color codes stripped
 * by getString()) still match.
 */
public class DonutPayColorClient implements ClientModInitializer {

	// Matches plain amounts ($100, $1,234.56) AND abbreviated ones Donut SMP
	// uses for big payments ($1.6b, $250k, $2.4m, $1t). The trailing
	// [kKmMbBtT]? is optional so both styles are matched by the same group.
	// e.g. "You paid Steve123 $ 1.6b" or "You paid Steve $50"
	private static final Pattern YOU_PAID = Pattern.compile(
			"(?i)(.*\\byou\\s+paid\\b.*?)(\\$\\s*[0-9][0-9,]*(?:\\.[0-9]+)?(?:[kmbt](?![a-z]))?)(.*)"
	);

	// e.g. "Steve123 paid you $ 1.6b"
	private static final Pattern PAID_YOU = Pattern.compile(
			"(?i)(.*\\bpaid\\s+you\\b.*?)(\\$\\s*[0-9][0-9,]*(?:\\.[0-9]+)?(?:[kmbt](?![a-z]))?)(.*)"
	);

	@Override
	public void onInitializeClient() {
		// Covers server/system broadcast messages, which is how most
		// economy plugins (including Donut SMP style payment notices)
		// send these lines.
		ClientReceiveMessageEvents.MODIFY_GAME.register((message, overlay) -> recolor(message));
	}

	private Text recolor(Text message) {
		String plain = message.getString();

		Matcher paidYouMatcher = PAID_YOU.matcher(plain);
		if (paidYouMatcher.matches()) {
			return buildColored(paidYouMatcher, Formatting.GREEN);
		}

		Matcher youPaidMatcher = YOU_PAID.matcher(plain);
		if (youPaidMatcher.matches()) {
			return buildColored(youPaidMatcher, Formatting.RED);
		}

		return message;
	}

	private MutableText buildColored(Matcher matcher, Formatting color) {
		String prefix = matcher.group(1);
		String amount = matcher.group(2);
		String suffix = matcher.group(3);

		MutableText result = Text.literal(prefix);
		result.append(Text.literal(amount).formatted(color));
		result.append(Text.literal(suffix));
		return result;
	}
}
